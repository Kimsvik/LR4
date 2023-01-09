package LR4;

import org.example.DfHelper;
import org.example.BConsumer.SendRequest;
import org.example.BDistributor.AcceptRequest;
import org.example.Topic.ProducerData;
import org.example.BProducer.ReceiveTopicName;
import org.example.CFG.ConsumerCFG;
import org.example.CFG.ProducerCFG;
import org.example.TimeClass;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AgentTest {

    private JadeTestingKit kit = new JadeTestingKit();
    private SendRequest inner = null;
    private Behaviour resBeh;

    private long time = TimeClass.getTime() + 100;

    @Test
    @SneakyThrows
    void scene1Test(){
        createDistributor("D1");
        createDistributor("D2");
        createDistributor("D3");
        createProducer("P1", 50);
        Thread.sleep(100);
        createConsumer("L1", 5, 100);
        createConsumer("L2", 7, 200);
        createConsumer("L3", 8, 150);
        Thread.sleep(500);
        Thread.sleep(time);
        resBeh = inner.getBeh();
        Thread.sleep(500);
        Assertions.assertEquals(0, resBeh.onEnd());
        }
    /*
    Торги с единственным производителем. Задать такое количество покупаемой мощности,
    чтобы только 1 поставщик смог удовлетворить запросу.
    Ожидаемый результат: агент-производитель продает по завышенной цене мощность,
    однако контракт отклоняется поставщиком из-за большой цены.
    */


    @Test
    @SneakyThrows
    void scene2Test(){
        createDistributor("D1");
        createProducer("P1", 15);
        createProducer("P2", 13);
        Thread.sleep(100);
        createConsumer("L1", 10, 300);
        Thread.sleep(500);
        Thread.sleep(time);
        resBeh = inner.getBeh();
        Thread.sleep(500);
        Assertions.assertEquals(1, resBeh.onEnd());
    }
    /*
    Успешный аукцион с двумя участниками. Задать такое количество покупаемой мощности,
    чтобы два поставщика смогли удовлетворить запросу и начали процесс снижения цены.
    Ожидаемый результат: агенты соревнуются друг с другом для право продать ЭЭ,
    и один из агентов-производителей продает по удовлетворительной цене запрошенную мощность.
    */

    @Test
    @SneakyThrows
    void scene3Test(){
        createDistributor("D1");
        createProducer("P1", 7);
        createProducer("P2", 8);
        Thread.sleep(100);
        createConsumer("L1", 10, 400);
        Thread.sleep(500);
        Thread.sleep(time);
        resBeh = inner.getBeh();
        Thread.sleep(500);
        Assertions.assertEquals(2, resBeh.onEnd());
    }
    /*
    Дефицит мощности в системе. Задать такое количество покупаемой мощности,
    что ни один производитель не может полностью удовлетворить запрос.
    Ожидаемый результат: агент дистрибьютор должен разбить контракт на несколько частей
    и закупить требуемое количество у различных поставщиков.
    */


    @BeforeEach
    void beforeEach(){
        kit.startJade();
        TimeClass.resetTime();
    }

    @AfterEach
    void afterEach() {
        kit.deadAllAgent();
    }

    void createDistributor(String name) {
        kit.createAgent(name, new OneShotBehaviour() {
            @Override
            public void action() {
                DfHelper.registerAgent(myAgent, "distributor:L" +myAgent.getLocalName().substring(1));
                myAgent.addBehaviour(new AcceptRequest());
            }

        });
    }

    void createProducer(String name, double power) {
        kit.createAgent(name, new OneShotBehaviour() {
            @Override
            public void action() {
                DfHelper.registerAgent(myAgent, "producer");
                ProducerCFG cfg = new ProducerCFG();
                cfg.setA(power);
                cfg.setType("TPS");
                myAgent.addBehaviour(new ReceiveTopicName(new ProducerData(cfg)));
            }

        });
    }


    void createConsumer(String name, double power, double maxPrice) {
        kit.createAgent(name, new OneShotBehaviour() {
            @Override
            public void action() {
                DfHelper.registerAgent(myAgent, "consumer:" + myAgent.getLocalName());
                ConsumerCFG cfg = new ConsumerCFG();
                for (int i = 0; i < 24; i++) {
                    cfg.getLoad().add(100.0);
                }
                cfg.setPnom(power);
                cfg.setMaxPrice(maxPrice);
                myAgent.addBehaviour(inner = new SendRequest(cfg ));
            }

        });
    }
}
