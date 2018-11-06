package controllers;

import daos.DaoManager;
import util.DilemmaSubmitData;

public class AddDilemmaController extends DilemmaController {

    public AddDilemmaController(AppController appController) {
        super(appController);
    }

    public void handleBackBtnClick() {
        appController.switchToDilemmaListView();
    }

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

