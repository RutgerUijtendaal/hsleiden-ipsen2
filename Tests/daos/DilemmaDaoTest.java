package daos;

import models.Dilemma;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DilemmaDaoTest {

    @Test
    void getAll() {
    }

    @Test
    void getById() {
    }

    @Test
    void save() {
        DaoManager.getDilemmaDao().save(new Dilemma((short) 13,"testTheme","https://test.com"));
    }

    @Test
    void update() {
        DaoManager.getDilemmaDao().update(new Dilemma(1,(short) 69,"testTheme","https://test.nl"));
    }

    @Test
    void delete() {
        DaoManager.getDilemmaDao().delete(new Dilemma(1,(short) 69,"testTheme","https://test.nl"));
        DaoManager.getDilemmaDao().delete(new Dilemma(2,(short) 69,"testTheme","https://test.nl"));
    }
}