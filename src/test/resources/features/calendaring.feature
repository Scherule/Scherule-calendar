Feature: Calendaring feature

  Scenario: User can create meeting
    Given there is a organizer 'Fred' who wants to schedule a meeting
    And this meeting has to happen in period '2017-10-02T14:00Z/2017-10-06T16:00Z'
    And this meeting has minimum participants count of '2'
    And this meeting has a participant 'Olga' with importance '0'
    And this participant declares availability '2017-10-02T14:00Z/2017-10-06T16:00Z'
    When the organizer creates this meeting
    Then this meeting is created
    And management key bound to 'Fred' can be obtained for this meeting
    And participation key bound to participant 'Olga' can be obtained for this meeting