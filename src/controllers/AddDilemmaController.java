package controllers;

import daos.DaoManager;
import util.DilemmaSubmitData;

/**
 * Controller class behind the AddEditDilemmaView
 * Handles adding a new dilemma to the database
 *
 * @author Jordi Dorren, Rutger Uijtendaal
 */
public class AddDilemmaController extends DilemmaController {

    public AddDilemmaController(AppController appController) {
        super(appController);
    }

    public void handleBackBtnClick() {
        appController.switchToDilemmaListView();
    }

    /**
     * Handles submitting the data to the database
     *
     * @param dilemmaSubmitData the data that is to be submitted
     * @see daos.DilemmaDao#dilemmaExists(Short)
     * @see controllers.AddDilemmaController#trySubmitDilemma()
     * @see AddDilemmaController#tryUploadPictures()
     */
    @Override
    public void handleSubmitBtnClick(DilemmaSubmitData dilemmaSubmitData) {
        this.dilemmaSubmitData = dilemmaSubmitData;

        // If a dilemma for weekNr already exists
        if(DaoManager.getDilemmaDao().dilemmaExists(Short.parseShort(dilemmaSubmitData.getWeekNr()))) {
            addEditDilemmaView.displayError("Dilemma van week " + dilemmaSubmitData.getWeekNr() + " bestaat al.");
            return;
        }

        if(!trySubmitDilemma()) {
            addEditDilemmaView.displayError("Fout tijdens het opslaan van dilemma");
            return;
        }

        // If the dilemma has pictures upload them to web and save their url.
        if(this.dilemmaSubmitData.hasPictures) {
            if(!tryUploadPictures()) {
                addEditDilemmaView.displayError("Fout tijdens het uploaden van plaatjes");
                return;
            }
        }

        appController.switchToAdminMenuView();

        appController.getActiveView().displayPopup("Dilemma toegevoegd.");

    }

    /**
     * Does the actual submitting to the database
     *
     * @see daos.DilemmaDao#save(models.DatabaseObject)
     * @see daos.AnswerDao#save(models.DatabaseObject)
     * @return true if the submitting went properly
     */
    private boolean trySubmitDilemma() {
        int dilemmaId = DaoManager.getDilemmaDao().save(dilemmaSubmitData.getDilemma());
        int answerOneId = DaoManager.getAnswerDao().save(dilemmaSubmitData.getAnswerA(dilemmaId));
        int answerTwoId = DaoManager.getAnswerDao().save(dilemmaSubmitData.getAnswerB(dilemmaId));

        // Save the ID in submit data to use with possible image uploads
        dilemmaSubmitData.setaOneId(answerOneId);
        dilemmaSubmitData.setaTwoId(answerTwoId);

        return true;
    }

}

