package com.lizmahoney401.Taskmaster;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.lizmahoney401.Taskmaster.Model.Taskmaster;
import com.lizmahoney401.Taskmaster.Model.TaskmasterRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TaskmasterApplication.class)
@WebAppConfiguration
@ActiveProfiles("local")
public class TaskmasterApplicationTest {

    private DynamoDBMapper dynamoDBMapper;

    @Autowired
    private AmazonDynamoDB amazonDynamoDB;

    @Autowired
    TaskmasterRepository repository;

    private static final String EXPECTED_TITLE = "Clean Dog cage";
    private static final String EXPECTED_DESCRIPTION = "Scrub the walls";
    private static final String EXPECTED_STATUS = "available";
    private static final String EXPECTED_ASSIGNEE = "Liz";

    @Before
    public void setup() throws Exception {
        dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);

        CreateTableRequest tableRequest = dynamoDBMapper.generateCreateTableRequest(Taskmaster.class);

        tableRequest.setProvisionedThroughput(new ProvisionedThroughput(1L, 1L));

        dynamoDBMapper.batchDelete((List<Taskmaster>)repository.findAll());
    }

    @Test
    public void readWriteTestCase() {
        String uuid = String.valueOf(UUID.randomUUID());
        Taskmaster newTask = new Taskmaster(uuid, EXPECTED_TITLE, EXPECTED_DESCRIPTION, EXPECTED_STATUS,
                EXPECTED_ASSIGNEE);
        repository.save(newTask);

        List<Taskmaster> result = (List<Taskmaster>) repository.findAll();

        assertTrue("Not empty", result.size() > 0);
        assertTrue("Contains item with expected title", result.get(0).getTitle().equals(EXPECTED_TITLE));
    }
}
