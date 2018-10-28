package controllers;

import models.Dilemma;
import models.Answer;
import daos.DaoManager;
import daos.AnswerDao;
import util.DilemmaSubmitData;

import java.io.IOException;
import java.io.File;

public class EditDilemmaController extends DilemmaController {

    public EditDilemmaController(AppController appCtl) {
        super(appCtl);
    }

    @Override
    public void handleSubmitBtnClick(DilemmaSubmitData dsd) {
        System.out.println("click from: " + this);
        
        dilemmaSubmitData = dsd;

        if(!trySubmitDilemma()) {
            aedv.displayError("Fout tijdens het opslaan van dilemma");
            return;
        }

        // If the dilemma has pictures upload them to web and save their url.
        if(dilemmaSubmitData.hasPictures) {
            if(!tryUploadPictures()) {
                aedv.displayError("Fout tijdens het uploaden van plaatjes");
                return;
            }
        }

        aedv.displayPopup("Dilemma aangepast");
    }

    private boolean trySubmitDilemma() {

        Dilemma dilemma = dilemmaSubmitData.getDilemma();
        dilemma.setId(dilemmaSubmitData.getDilemmaId());

        Answer answerA = dilemmaSubmitData.getAnswerA(dilemma.getId());
        Answer answerB = dilemmaSubmitData.getAnswerB(dilemma.getId());
        answerA.setId(dilemmaSubmitData.getaOneId());
        answerB.setId(dilemmaSubmitData.getaTwoId());

        DaoManager.getDilemmaDao().update(dilemma);
        DaoManager.getAnswerDao().update(answerA);
        DaoManager.getAnswerDao().update(answerB);

        return true;
    }

    public void fillFields(Dilemma dilemma) {
        AnswerDao answerDao = DaoManager.getAnswerDao();
        System.out.println(dilemma);
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

        aedv.fillFields(dilemma, answers, file1, file2);
    }
}

