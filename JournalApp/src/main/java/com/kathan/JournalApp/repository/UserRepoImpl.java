package com.kathan.JournalApp.repository;

import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.kathan.JournalApp.entities.Users;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class UserRepoImpl {
	private final MongoTemplate mongoTemplate;
	
	public List<Users> getUserForSA(){
		
		Query query = new Query();
		
		query.addCriteria(Criteria.where("email").ne(null).ne(""));
		query.addCriteria(Criteria.where("sentimentAnalysis").is(true));
		
		//Or Query Example
		Criteria criteria = new Criteria();
		criteria.orOperator(Criteria.where("email").exists(true),Criteria.where("sentimentAnalysis").is(true));
		Query query2 = new Query(criteria);
		List<Users> anyOneCondTrue = mongoTemplate.find(query2,Users.class);
		log.info("OR condition : "+anyOneCondTrue);
		
		//Arrays example
		query.addCriteria(Criteria.where("roles").in("USER","ADMIN"));
		
		log.info("AND Condition: "+ mongoTemplate.find(query, Users.class));
		return mongoTemplate.find(query, Users.class);
	}
}
