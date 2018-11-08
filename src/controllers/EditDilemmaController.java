package controllers;

import daos.AnswerDao;
import daos.DaoManager;
import models.Answer;
import models.Dilemma;
import util.DilemmaSubmitData;

import java.io.File;
import java.io.IOException;

public class EditDilemmaController extends DilemmaController {

    public EditDilemmaController(AppController appController) {
        super(appController);
    }

    @Override
    public void handleSubmitBtnClick(DilemmaSubmitData dilemmaSubmitData) {
        this.dilemmaSubmitData = dilemmaSubmitData;

        if(!trySubmitDilemma()) {
            addEditDilemmaView.displayError("Fout tijdens het opslaan van dilemma");
            return;
        }

        // If the dilemma has pictures upload them to web and save their url.
        if(this.dilemmaSubmitData.hasPictures) {
            if(!tryUploadPictures()) {
                addEditDilemmaView.displayError("Fout tijdens het uploaden van afbeeldingen");
                return;
            }
        }

        addEditDilemmaView.displayPopup("Dilemma aangepast");
    }

    private boolean trySubmitDilemma() {

        Dilemma dilemma = dilemmaSubmitData.getDilemma();
        dilemma.setId(dilemmaSubmitData.getDilemmaId());

        Answer answerA = dilemmaSubmitData.getAnswerA(dilemma.getId());
        Answer answerB = dilemmaSubmitData.getAnswerB(dilemma.getId());
        answerA.setId(dilemmaSubmitData.getaOneId());
        answerB.setId(dilemmaSubmitData.getaTwoId());

        try {
            DaoManager.getDilemmaDao().update(dilemma);
            DaoManager.getAnswerDao().update(answerA);
            DaoManager.getAnswerDao().update(answerB);
        } catch(Exception e) {
            return false;
        }

        return true;
    }

    public void fillFields(Dilemma dilemma) {
        AnswerDao answerDao = DaoManager.getAnswerDao();
        Answer[] answers = answerDao.getByDilemmaId(dilemma.getId());
        File file1, file2;
        file1 = file2 = null;

        if (answers[0].getUrl() != null && answers[1].getUrl() != null) {
            try {
                file1 = imageService.getAnswerImageFile(answers[0]);
                file2 = imageService.getAnswerImageFile(answers[1]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        addEditDilemmaView.fillFields(dilemma, answers, file1, file2);
    }
}

