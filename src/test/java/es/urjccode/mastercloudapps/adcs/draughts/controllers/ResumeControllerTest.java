package es.urjccode.mastercloudapps.adcs.draughts.controllers;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import es.urjccode.mastercloudapps.adcs.draughts.models.Session;
import es.urjccode.mastercloudapps.adcs.draughts.models.StateValue;

public class ResumeControllerTest {

    Session session;
    ResumeController resumeController;

    public ResumeControllerTest() {
        session = new Session();
        resumeController = new ResumeController(session);
    }

    @Test
    public void givenResumeControllerWhenResumeGameMoveToInitialStateRequiereCorrectThenNotError() {
        assertEquals(StateValue.INITIAL, session.getValueState());
        resumeController.next();
        assertEquals(StateValue.IN_GAME, session.getValueState());
        resumeController.next();
        assertEquals(StateValue.FINAL, session.getValueState());
        resumeController.reset();
        assertEquals(StateValue.INITIAL, session.getValueState());
    }

    @Test(expected = AssertionError.class)
    public void givenResumeControllerWhenResumeGameMoveOutThenError() {
        for (StateValue state : StateValue.values()) {
            assertEquals(state, session.getValueState());
            resumeController.next();
        }
    }
}