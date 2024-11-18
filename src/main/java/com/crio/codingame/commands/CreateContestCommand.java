package com.crio.codingame.commands;

import java.util.List;

import com.crio.codingame.entities.Contest;
import com.crio.codingame.entities.Level;
import com.crio.codingame.exceptions.ContestNotFoundException;
import com.crio.codingame.exceptions.QuestionNotFoundException;
import com.crio.codingame.exceptions.UserNotFoundException;
import com.crio.codingame.services.IContestService;

public class CreateContestCommand implements ICommand{

    private final IContestService contestService;

    public CreateContestCommand(IContestService contestService) {
        this.contestService = contestService;
    }

    // TODO: CRIO_TASK_MODULE_CONTROLLER
    // Execute create method of IContestService and print the result.
    // Also Handle Exceptions and print the error messsages if any.
    // Look for the unit tests to see the expected output.
    // Sample Input Token List:- ["CREATE_CONTEST","CRIODO2_CONTEST","LOW Monica","40"]
    // or
    // ["CREATE_CONTEST","CRIODO1_CONTEST","HIGH","Ross"]
    // Hint - Use Parameterized Exceptions in the Service class to match with the Unit Tests Output.

    @Override
    public void execute(List<String> tokens) {
        try {
            Integer numQuestions = tokens.size()>4 ? Integer.valueOf(tokens.get(4)) : null;
        Contest createdContext = contestService.create(tokens.get(1), Level.valueOf(tokens.get(2)),
             tokens.get(3), numQuestions);
        System.out.println(createdContext);
        } catch(UserNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch(ContestNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch(QuestionNotFoundException ex) {
            System.out.println(ex.getMessage());
        } 
    }
    
}
