package com.crio.codingame.services;

import java.util.List;
//import java.util.stream.Collectors;
import java.util.Optional;
import com.crio.codingame.dtos.UserRegistrationDto;
import com.crio.codingame.entities.Contest;
import com.crio.codingame.entities.ContestStatus;
import com.crio.codingame.entities.RegisterationStatus;
import com.crio.codingame.entities.ScoreOrder;
import com.crio.codingame.entities.User;
import com.crio.codingame.exceptions.ContestNotFoundException;
import com.crio.codingame.exceptions.InvalidOperationException;
import com.crio.codingame.exceptions.UserNotFoundException;
import com.crio.codingame.repositories.IContestRepository;
import com.crio.codingame.repositories.IUserRepository;
import java.util.Collections;
import java.util.Comparator;

public class UserService implements IUserService {

    private final IUserRepository userRepository;
    private final IContestRepository contestRepository;

    public UserService(IUserRepository userRepository, IContestRepository contestRepository) {
        this.userRepository = userRepository;
        this.contestRepository = contestRepository;
    }
    // TODO: CRIO_TASK_MODULE_SERVICES
    // Create and store User into the repository.
    @Override
    public User create(String name) {
        User newUser = new User(name, 0);
        return this.userRepository.save(newUser);
    //  return null;
    }

    // TODO: CRIO_TASK_MODULE_SERVICES
    // Get All Users in Ascending Order w.r.t scores if ScoreOrder ASC.
    // Or
    // Get All Users in Descending Order w.r.t scores if ScoreOrder DESC.

    @Override
    public List<User> getAllUserScoreOrderWise(ScoreOrder scoreOrder){
        List<User> allUsers = this.userRepository.findAll();
        if(scoreOrder.equals(ScoreOrder.ASC)) {
            Collections.sort(allUsers, new AscendingScoreComparator());
        } else {
            Collections.sort(allUsers, new DescendingScoreComparator());
        }
     return allUsers;
    }

    class AscendingScoreComparator implements Comparator<User>{
        @Override
        public int compare(User user1, User user2) {
            return user1.getScore().compareTo(user2.getScore());
        }
    }

    class DescendingScoreComparator implements Comparator<User>{
        @Override
        public int compare(User user1, User user2) {
            return user2.getScore().compareTo(user1.getScore());
        }
    }

    @Override
    public UserRegistrationDto attendContest(String contestId, String userName) throws ContestNotFoundException, UserNotFoundException,InvalidOperationException {
        Contest contest = contestRepository.findById(contestId).orElseThrow(ContestNotFoundException::new);
        User user = userRepository.findByName(userName).orElseThrow(UserNotFoundException::new);
        if(contest.getContestStatus().equals(ContestStatus.IN_PROGRESS))
        {
            throw new InvalidOperationException("Cannot Withdraw Contest. Contest for given id:"+contestId+" is in progress!");
        }
        if(contest.getContestStatus().equals(ContestStatus.ENDED))
        {
            throw new InvalidOperationException("Cannot Withdraw Contest. Contest for given id:"+contestId+" is ended!");
        }
        if(contest.getCreator().equals(user))
        {
            throw new InvalidOperationException("Cannot Withdraw Contest. User for given contest id:"+contestId+" is not registered!");
        }
        if(user.checkIfContestExists(contest))
        {
            throw new InvalidOperationException("Cannot Withdraw Contest. User for given contest id:"+contestId+" is not registered!");
        }
        user.addContest(contest);
        userRepository.save(user);
        return new UserRegistrationDto(contest.getName(), user.getName(),RegisterationStatus.REGISTERED);
    }

    // TODO: CRIO_TASK_MODULE_SERVICES
    // Withdraw the user from the contest
    // Hint :- Refer Unit Testcases withdrawContest method

    @Override
    public UserRegistrationDto withdrawContest(String contestId, String userName) throws ContestNotFoundException, UserNotFoundException, InvalidOperationException {
        
        // UserRegistrationDto udt = attendContest(contestId,userName);
        Contest contest = contestRepository.findById(contestId).orElseThrow(ContestNotFoundException::new);
        User user = userRepository.findByName(userName).orElseThrow(UserNotFoundException::new);
        ContestStatus contestStatus =  contest.getContestStatus();
        if(contestStatus.equals(ContestStatus.IN_PROGRESS))
        {
            throw new InvalidOperationException("Cannot Withdraw Contest. Contest for given id:"+contestId+" is in progress!");
        }
        if(contestStatus.equals(ContestStatus.ENDED))
        {
            throw new InvalidOperationException("Cannot Withdraw Contest. Contest for given id:"+contestId+" is ended!");
        }
        if (!user.checkIfContestExists(contest))
        {
            throw new InvalidOperationException("Cannot Withdraw Contest. User for given contest id:"+contestId+" is not registered!");
        }
        if(contest.getCreator().equals(user))
        {
            throw new InvalidOperationException();
        }
        user.deleteContest(contest);
        userRepository.save(user);
        return new UserRegistrationDto(contest.getName(), user.getName(),RegisterationStatus.NOT_REGISTERED);
    }
    
}
