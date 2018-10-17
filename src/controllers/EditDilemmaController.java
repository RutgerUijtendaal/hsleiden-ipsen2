package controllers;

import models.Dilemma;
import models.Answer;
import daos.DaoManager;
import daos.AnswerDao;
import util.DilemmaSubmitData;

public class EditDilemmaController extends DilemmaController {

    public EditDilemmaController(AppController appCtl) {
        super(appCtl);
    }

    @Override
    public void handleSubmitBtnClick(DilemmaSubmitData dilemmaSubmitData) {
        System.out.println("click from: " + this);
    }

    public void fillFields(Dilemma dilemma) {
        AnswerDao answerDao = DaoManager.getAnswerDao();
        System.out.println(dilemma);
        Answer[] answers = answerDao.getByDilemmaId(dilemma.getId());
        aedv.fillFields(dilemma, answers);
    }
}

