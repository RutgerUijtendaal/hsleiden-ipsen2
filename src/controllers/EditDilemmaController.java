package controllers;

import models.Dilemma;
import models.Answer;
import daos.DaoManager;
import daos.AnswerDao;
import util.DilemmaSubmitData;

import javafx.scene.image.Image;
import java.io.IOException;
import java.io.File;

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

