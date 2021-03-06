package controllers;

import daos.AnswerDao;
import daos.DaoManager;
import models.Answer;
import models.Dilemma;
import util.DilemmaSubmitData;

import java.io.File;
import java.io.IOException;

/**
 * One of the child classes for the DilemmaController
 * handles the logic for editting a dilemma in the AddEditDilemmaView
 *
 * @author Stefan de Keijzer
 * @author Jordi Dorren
 */
public class EditDilemmaController extends DilemmaController {

    public EditDilemmaController(AppController appController) {
        super(appController);
    }

    /**
     * Edit the dilemma by updating the database data for that dilemma
     *
     * @param dilemmaSubmitData uses this object for updating the data in the database
     * @see controllers.EditDilemmaController#trySubmitDilemma()
     */
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

    /**
     * Tries to actually send the data to the database
     *
     * @see daos.DilemmaDao#update(models.DatabaseObject)
     * @see daos.AnswerDao#update(models.DatabaseObject)
     * @returns true if succesfull, false if unsuccesfull
     */
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

    /**
     * Fills the fields of the existing view with the data from the dilemma
     * downloads the existing pictures and puts them into the view as
     * objects for easy access
     *
     * @param dilemma needed for knowing how to fill fields
     * @see daos.AnswerDao#getByDilemmaId(int)
     * @see service.ImageService#getAnswerImageFile(Answer)
     * @see views.AddEditDilemmaView#fillFields(Dilemma, Answer[], File, File)
     */
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

