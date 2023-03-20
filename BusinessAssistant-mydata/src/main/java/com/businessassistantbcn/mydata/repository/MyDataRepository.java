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
    private IUserSearchesRepository userSearchesRepository;

    public String getMyData() {

        List<UserSearch> search = userSearchesRepository.findBySearchUuid("49E7DD6D-A774-CC81-C2B6-ED223E872397");


        while(search.size() > 0){
            System.out.println("*****"+search.get(0).getSearchUuid());
            search.remove(0);
        }


        return "MyData";
    }
}
