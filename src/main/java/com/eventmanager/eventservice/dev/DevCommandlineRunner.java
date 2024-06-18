package com.eventmanager.eventservice.dev;

import com.eventmanager.eventservice.dao.*;
import com.eventmanager.eventservice.model.*;
import com.eventmanager.eventservice.model.enums.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.*;
import java.util.List;

@Component
@Profile("dev")
@RequiredArgsConstructor
public class DevCommandlineRunner implements CommandLineRunner {

    private final EventTypeRepository eventTypeRepository;
    private final EventRepository eventRepository;
    private final GuestRepository guestRepository;
    private final OrganizerRepository organizerRepository;
    private final ParticipantRepository participantRepository;
    private final UserCredentialsRepository userCredentialsRepository;
    private final RequestRepository requestRepository;
    private final ChecklistRepository checklistRepository;
    private final TaskRepository taskRepository;
    private final BudgetCategoryRepository budgetCategoryRepository;
    private final PaymentRepository paymentRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;
    private final InvitationRepository invitationRepository;

    @Override
    public void run(String... args) throws Exception {
        //************ EVENT TYPES ***************
        EventType type1 = new EventType();
        type1.setId(1L);
        type1.setName("wedding");
        type1.setDescription("Свадьба");
        eventTypeRepository.save(type1);

        EventType type2 = new EventType();
        type2.setId(2L);
        type2.setName("birthday");
        type2.setDescription("День рождения");
        eventTypeRepository.save(type2);

        EventType type3 = new EventType();
        type3.setId(3L);
        type3.setName("work");
        type3.setDescription("Корпоратив");
        eventTypeRepository.save(type3);

        EventType type4 = new EventType();
        type4.setId(4L);
        type4.setName("conference");
        type4.setDescription("Конференция");
        eventTypeRepository.save(type4);

        EventType type5 = new EventType();
        type5.setId(5L);
        type5.setName("other");
        type5.setDescription("Иное торжество");
        eventTypeRepository.save(type5);

        //        *********************** ORGANIZER USER ******************************
        Authority authority = new Authority(1l, AuthorityName.ROLE_ORGANIZER);
        authorityRepository.save(authority);

        String result = passwordEncoder.encode("12345678");

        UserCredentials user1 = new UserCredentials();
        user1.setId(1l);
        user1.setLogin("org");
        user1.setPassword(result);
        user1.setRole(authority);
        userCredentialsRepository.save(user1);

        System.out.println(result);
        System.out.println(passwordEncoder.matches("12345678", result));

        Organizer organizer = new Organizer();
        organizer.setId(1L);
        organizer.setName("Анна");
        organizer.setSurname("Монголова");
        organizer.setUser(user1);
        organizerRepository.save(organizer);

        //************* EVENTS ***********************
        Budget budget = new Budget();
        budget.setId(1L);
        budget.setExpectedAmount(10000);

        Event event = new Event();
        event.setId(1L);
        event.setName("Свадьба Валеры и Карины");
        event.setDescription("Свадьба Валеры и Карины");
        event.setType(type1);
        event.setDate(LocalDate.of(2024, 7, 16));
        event.setTime(LocalTime.of(15, 30));
        event.setUuid("9865e19263a2");
        event.setPlace("Усадьба Володино");
        event.setBudget(budget);
        event.getUserCredentialsList().add(user1);
        eventRepository.save(event);

        Invitation invitation = new Invitation();
        invitation.setId(1L);
        invitation.setHeader("Invitation");
        invitation.setEvent(event);
        invitationRepository.save(invitation);


//        ******************** GUESTS **********************
        Guest guest1 = new Guest();
        guest1.setId(1l);
        guest1.setName("Наталья");
        guest1.setSurname("Лукашевич");
        guest1.setGender(Gender.WOMAN);
        guest1.setRvspStatus(RVSPStatus.CONFIRMED);
        guest1.setUuid("2142cb3a-63a2-11ee-8c99-0242ac120002");
        guest1.setEvent(event);
        guest1.setEmail("mama@mail.ru");
        guestRepository.save(guest1);

        Guest guest2 = new Guest();
        guest2.setId(2l);
        guest2.setName("Виталий");
        guest2.setSurname("Лукашевич");
        guest2.setGender(Gender.MAN);
        guest2.setRvspStatus(RVSPStatus.UNDEFINED);
        guest2.setUuid("2142cb3a-63a2-11ee-8c99-0242ac120003");
        guest2.setEvent(event);
        guest2.setEmail("papa@mail.ru");
        guestRepository.save(guest2);

        Guest guest3 = new Guest();
        guest3.setId(3l);
        guest3.setName("Светлана");
        guest3.setSurname("Русецкая");
        guest3.setGender(Gender.WOMAN);
        guest3.setRvspStatus(RVSPStatus.DECLINED);
        guest3.setUuid("2142cb3a-63a2-11ee-8c99-0242ac120004");
        guest3.setEvent(event);
        guestRepository.save(guest3);



//        *********************** PARTICIPANT USER *************************
        Authority authority2 = new Authority(2l, AuthorityName.ROLE_USER);
        authorityRepository.save(authority2);

        String result1 = passwordEncoder.encode("part1234");

        UserCredentials user2 = new UserCredentials();
        user2.setId(2l);
        user2.setLogin("part");
        user2.setPassword(result1);
        user2.setRole(authority2);
        userCredentialsRepository.save(user2);

        Participant participant = new Participant();
        participant.setId(1L);
        participant.setName("Карина");
        participant.setSurname("Лукашевич");
        participant.setUser(user2);
        participant.setEmail("hodona7408@neixos.com");
        participantRepository.save(participant);

//        ****************************** REQUESTS ****************************
        Request request = new Request();
        request.setId(1l);
        request.setEventName("День рождения");
        request.setEventDescription("День рождения Ирина 30 лет");
        request.setEventDate(LocalDate.of(2024, 7, 1));
        request.setEventTime(LocalTime.of(12, 0));
        request.setEventPlace("Diamond");
        request.setStatus(RequestStatus.CREATED);
        request.setOrganizer(organizer);
        request.setParticipant(participant);
        request.setType(type2);
        request.getUserCredentialsList().addAll(List.of(user1, user2));
        requestRepository.save(request);

        checklists(event);
        budget(budget);

    }

    private void budget(Budget budget) {
        BudgetCategory budgetCategory = new BudgetCategory();
        budgetCategory.setId(1L);
        budgetCategory.setName("Vending");
        budgetCategory.setDescription("Подбор площадки, декоратора, меню");
        budgetCategory.setBudget(budget);
        budgetCategory.setPlannedAmount(3000);
        budgetCategoryRepository.save(budgetCategory);

        Payment payment = new Payment();
        payment.setId(1L);
        payment.setExpenseName("Усадьба");
        payment.setDescription("Усадьба Володино");
        payment.setAmount(1500);
        payment.setBudgetCategory(budgetCategory);
        paymentRepository.save(payment);

        Payment payment2 = new Payment();
        payment2.setId(2L);
        payment2.setExpenseName("Декор");
        payment2.setDescription("Декаротор + допполнительный декор");
        payment2.setAmount(1000);
        payment2.setBudgetCategory(budgetCategory);
        paymentRepository.save(payment2);

        BudgetCategory budgetCategory2 = new BudgetCategory();
        budgetCategory2.setId(2L);
        budgetCategory2.setName("Dressing");
        budgetCategory2.setDescription("Подбор образов жениха и невесты");
        budgetCategory2.setBudget(budget);
        budgetCategory2.setPlannedAmount(1500);
        budgetCategoryRepository.save(budgetCategory2);

        Payment payment3 = new Payment();
        payment3.setId(3L);
        payment3.setExpenseName("Образ невесты");
        payment3.setDescription("Платье и туфли");
        payment3.setAmount(1000);
        payment3.setBudgetCategory(budgetCategory2);
        paymentRepository.save(payment3);

        Payment payment4 = new Payment();
        payment4.setId(4L);
        payment4.setExpenseName("Образ жениха");
        payment4.setDescription("Костюм и туфли");
        payment4.setAmount(500);
        payment4.setBudgetCategory(budgetCategory2);
        paymentRepository.save(payment4);
    }

    private void checklists(Event event) {
        Checklist checklist = new Checklist();
        checklist.setId(1L);
        checklist.setEvent(event);
        checklist.setName("Подбор площадки");
        checklist.setDescription("подбор места проведения");
        checklistRepository.save(checklist);

        Task task1 = new Task();
        task1.setName("Найти площадку");
        task1.setDescription("Найти площадку");
        task1.setId(1L);
        task1.setDeadline(ZonedDateTime.of(2024, 07, 12, 12, 30, 0, 0, ZoneId.systemDefault()));
        task1.setStatus(TaskStatus.DONE);
        task1.setChecklist(checklist);
        taskRepository.save(task1);

        Task task2 = new Task();
        task2.setName("Внести задаток");
        task2.setDescription("Внести задаток 300$");
        task2.setId(2L);
        task2.setDeadline(ZonedDateTime.of(2024, 07, 12, 12, 30, 0, 0, ZoneId.systemDefault()));
        task2.setStatus(TaskStatus.PROGRESS);
        task2.setChecklist(checklist);
        taskRepository.save(task2);
    }
}
