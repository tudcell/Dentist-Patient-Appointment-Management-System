package tests.repository.memory;

import main.repository.memory.InMemoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tests.repository.TestEntity;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryRepositoryTest {
    private InMemoryRepository<Integer, TestEntity> repository;

    @BeforeEach
    public void setUp() {
        repository = new InMemoryRepository<>();
    }

    @Test
    public void testAdd_addNewEntity_successful(){
        TestEntity testEntity = new TestEntity(1, "Name");
        Integer id = repository.add(testEntity);

        assertTrue(repository.findById(id).isPresent(), "Entity should be added successfully!");
    }

    @Test
    public void testFindAll_addTwoEntitiesThenSizeIsTwo_successful(){
        TestEntity firstTestEntity = new TestEntity(1, "Name1");
        TestEntity secondTestEntity = new TestEntity(2, "Name2");

        repository.add(firstTestEntity);
        repository.add(secondTestEntity);
        Iterable<TestEntity> entities = repository.findAll();
        ArrayList<TestEntity> entityList = new ArrayList<>();
        entities.forEach(entityList::add);

        assertEquals(2,entityList.size());
    }

    @Test
    public void testFindById_addEntityThenFindById_successful(){
        TestEntity firstTestEntity = new TestEntity(1, "Name1");
        Integer id = repository.add(firstTestEntity);
        Optional<TestEntity> fountTestEntity = repository.findById(id);
        assertTrue(fountTestEntity.isPresent(), "Entity should be found successfully!");
    }

    @Test
    public void testModify_modifyOneEntity_successful(){
        TestEntity firstTestEntity = new TestEntity(1, "Name1");
        Integer id = repository.add(firstTestEntity);

        TestEntity newTestEntity = new TestEntity(1, "New Name");
        newTestEntity.setId(id);
        repository.modify(newTestEntity);

        Optional<TestEntity> fountTestEntity = repository.findById(id);
        assertEquals("New Name",fountTestEntity.get().getName());
    }

    @Test
    public void testDelete_addEntityThenDeleteIt_successful(){
        TestEntity testEntity = new TestEntity(1, "Name1");
        Integer id = repository.add(testEntity);

        repository.delete(id);
        Optional<TestEntity> fountTestEntity = repository.findById(id);
        assertFalse(fountTestEntity.isPresent(), "Entity should not be found successfully!");
    }
}
