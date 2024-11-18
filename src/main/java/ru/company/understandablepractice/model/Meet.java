package ru.company.understandablepractice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.company.understandablepractice.model.types.MeetingFormat;
import ru.company.understandablepractice.model.types.PaymentType;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "meet")
public class Meet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meet_id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "name_meet")
    private String nameMeet;

    @Column(name = "date_meet")
    private LocalDate dateMeet;

    @Column(name = "start_meet")
    @JsonFormat(pattern = "hh:mm:ss")
    private LocalTime startMeet;

    @Column(name = "end_meet")
    @JsonFormat(pattern = "hh:mm:ss")
    private LocalTime endMeet;

    @Column(name = "format_meet")
    private MeetingFormat formatMeet;

    @Column(name = "payment_type")
    private PaymentType paymentType;

    @Column(name = "next_date_meet")
    private LocalDate nextDayMeet;

    @Column(name = "next_start_meet")
    @JsonFormat(pattern = "hh:mm:ss")
    private LocalTime nextStartMeet;

    @Column(name = "next_end_meet")
    @JsonFormat(pattern = "hh:mm:ss")
    private LocalTime nextEndMeet;

    @Column(name = "client_session_request", columnDefinition = "TEXT")
    private String clientSessionRequest;

    @Column(name = "therapist_state_at_session_start", columnDefinition = "TEXT")
    private String therapistStateAtSessionStart;

    @Column(name = "main_topics_discussed_during_session", columnDefinition = "TEXT")
    private String mainTopicsDiscussedDuringSession;

    @Column(name = "client_key_phrases_insights", columnDefinition = "TEXT")
    private String clientKeyPhrasesInsights;

    @Column(name = "client_main_emotions", columnDefinition = "TEXT")
    private String clientMainEmotions;

    @Column(name = "therapist_main_emotions_expressed", columnDefinition = "TEXT")
    private String therapistMainEmotionsExpressed;

    @Column(name = "therapist_unexpressed_emotions", columnDefinition = "TEXT")
    private String therapistUnexpressedEmotions;

    @Column(name = "techniques_and_methods_used", columnDefinition = "TEXT")
    private String techniquesAndMethodsUsed;

    @Column(name = "client_main_obstacles_methods", columnDefinition = "TEXT")
    private String clientMainObstaclesMethods;

    @Column(name = "therapist_state_at_session_end", columnDefinition = "TEXT")
    private String therapistStateAtSessionEnd;

    @Column(name = "plan_next_session", columnDefinition = "TEXT")
    private String planNextSession;

    @Column(name = "supervision_them_and_problem", columnDefinition = "TEXT")
    private String supervisionThemAndProblem;
}
