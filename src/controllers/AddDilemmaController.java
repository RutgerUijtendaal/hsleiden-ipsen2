package controllers;

import daos.AnswerDao;
import daos.DaoManager;
import util.DilemmaSubmitData;
import service.ImageService;
import views.AddEditDilemmaView;
import views.BaseView;
import models.Dilemma;
import models.Answer;

import java.io.IOException;

public class AddDilemmaController extends DilemmaController {

    public AddDilemmaController(AppController appCtl) {
        super(appCtl);
    }

    @Override
    public void handleSubmitBtnClick(DilemmaSubmitData dilemmaSubmitData) {
        System.out.println("click from:  " + this);
    }

}

