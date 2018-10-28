package controllers;

import daos.DaoManager;
import util.DilemmaSubmitData;

public class AddDilemmaController extends DilemmaController {

    public AddDilemmaController(AppController appCtl) {
        super(appCtl);
    }

    public void handleBackBtnClick() {
        appCtl.switchToAdminMenuView();
    }

    @Override
    public void handleSubmitBtnClick(DilemmaSubmitData dsd) {
        dilemmaSubmitData = dsd;

        // If a dilemma for weekNr already exists
        if(DaoManager.getDilemmaDao().dilemmaExists(Short.parseShort(dsd.getWeekNr()))) {
            aedv.displayError("Dilemma van week " + dsd.getWeekNr() + " bestaat al.");
            return;
        }

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

        appCtl.switchToAdminMenuView();

        appCtl.getActiveView().displayPopup("Dilemma toegevoegd.");

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

