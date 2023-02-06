package com.businessassistantbcn.mydata.repository;


import com.businessassistantbcn.mydata.entity.UserSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class MyDataRepository implements IMyDataRepository {

    @Autowired
    private UserSearchesRepository userSearchesRepository;


}
