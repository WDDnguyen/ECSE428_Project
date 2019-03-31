# Previous readme
* Delete stuff above the line break before submission
ShredIt


### android application configuration
* Using Android Studio 3.3
    - Java 8
    - SDK 28
    - Build tools 28.0.3

### Some Report Guidelines

#### Report Headers
* For each header in the report, explain the following
- How did this apply?
- How did this evolve?

#### Log of Work done (EACH PERSON MUST DO THEIR OWN)
* One xls per team member
* Task relationship to story
* Who did the task
* When did it start
* When did it end
* Effort taken to complete task
* `Add tasks for the report as well`
* `Keep format similar to sprint task lists in the drive`
* `Submit these in the folder /project-docs/tasklists with the format "<name>_<id>_tasklist.xlsx"`

#### Form
* Terse (no unnecessary details)
* Generalizations (avoid)
* Spelling and grammar
* Format (consistent and not awkward)
* Flow (complete)
* Student names and IDs
* References (2 or more correctly identified)


***

# ECSE428-Group9 Project Report

### List of Contributors
Name | Student ID | Github
--- | --- | ---
Ben Willetts | 260610719 | @bendwilletts
William Nguyen | 260638465 | @WDDNguyen
Shi Yu Liu | 260683360 |
Jiawei Ni | 260615965 |
Wiam El Ouadi | 260663710 |
Aidan Sullivan | 260733921 | @aidssmcc
Patrick Ghazal | 260672468 |
Brian Kim-Lim | 260636766 | @briankimlim
Samuel Genois | 260692287 |
Julien Courbebaisse | 260614548 |
Ebou Jobe | 260664278 |
Luke Ma | 260745824 |
Volen Mihaylov | 260746982 |
Hank Zhang | 260684347 |

## Project Motivation
* Motivation/Reason for why the project should exist in the world.  `Luke`
* Make sure you explain what your project is and what problem it is solving. Why was the project needed: is it solving a problem not solved by other products? is it improving over the existing products?

## Design Choices
* Does it explain your design decisions, why you decided to choose one technology/framework/coding style/task distribution over the other `Shi Yu`

## Use of External Technology
* References/Links to every technology that you didn't create yourself (i.e. frameworks, IDEs, version control, issue tracker, cloud hosting platform) `Luke`

## Software Architecture
* Does it explain your entire software architecture `Ben`
* how is your code distributed among the module/packages/repos/

## Running/Testing the Application
* Does it explain how to run and test your project `Hank`

## Scrum Rituals
### Backlog Grooming
* How did this apply? <br />
After forming the group, a meeting was done to discuss about the project and ideas for the android application. After the initial discussion and ideas of the workout application, we created user stories for the application in the product backlog based on the gym workout generating ideas. Many user stories were created for the product backlog. We decided to mark each of these user stories with high, medium or low priorities. We only kept the high priorities stories to create a functional application and functional requirement needed to implement these user stories. We also written acceptance tests related to the high priority stories to have better understanding of the requirements of the story.

* How did this evolve? <br />
When we worked on the application, some of the user stories needed to be revised after discovering additional details to these stories. The user stories were adjusted based on new details or user stories that weren’t there. We added and adjusted the product backlog for some user stories and added clarification to the user stories in the product backlog.

### Sprint Planning
* How did this apply? <br />
Before each sprint, we set up a meeting to go through the product backlog and decide which user stories to accomplish for that sprint. Since we decided to only have 11 high priority user stories in the product backlog, we planned 6 user stories to be completed on the first sprint and 5 on the second sprint. The first sprint’s user stories focused on making a barebone android application that can log a user and generate a work. Also, the first sprint was to setup a database and initialize views for the application. The second sprint involved 5 user stories which new functionalities or refined sprint 1 functionality.

* How did this evolve? <br />
Based on the first sprint, some of the user stories could not be completed because of functional requirements that blocked these stories completion. On our second sprint, we decided to push the incomplete user stories with the sprint two stories. Some of these incomplete user stories were the base for some of the sprint 2 tasks. In the second sprint planning, we put more tasks to be done to complete the project by including the incomplete stories.

### Story Estimation
* Initial Estimations <br />
Initial Story estimations were intentionally over-estimations. Each story was split into several key component that would be considered as tasks. Specifically these were coding, data, testing, design of views, functional requirements, and management tasks. Some tasks would be shared between stories, such as the login and sign-up stories would share a view. Once these tasks were formed, a generous estimation was made of how long each of these tasks would take. This approach was fairly effective, but not without flaws. In some cases the time to complete a task was over-estimated and sometimes underestimated by several hours.

* Revising Estimations <br />
The issue with the previous system of story estimation was that the amount of estimated work was off by several hours frequently, and as such people would be working an inconsistent amount of time. Estimations got more accurate since developers become more familiar with the codebase, but it was also understood that mistakes would still be made. At the beginning of the second sprint, each of the stories that pulled over from the first sprint, and the stories from the second sprint were brought together and time estimates were remade. For the most part these would be more accurate to actual time spent. In addition, not all tasks were immediately assigned for sprint 2, to allow people with unexpectedly short initial tasks to be able to achieve the required hours for their work. By week 3 of sprint 2, all tasks were properly assigned, with the best possible time estimation.

### Daily Stand-up Meeting
* How did this apply? <br />
Because of scheduling issues and difficulty of gathering 14 members for a daily meeting, we decided that team members can update daily their task through online methods. However, there was a weekly meeting set up to gather the most number of members to discuss and clarify the tasks and progression of the project. In these meeting, members can communicate face to face or through voice chat their concerns, progression and upcoming task.

* How did this evolve? <br />
This stays reasonably the same throughout the entire project since team members were occupied with different classes and other commitments. The weekly meeting helped make sure clarification and update on the sprint planning were done. The daily online messaging helped members communicate their intentions and collaborate in different tasks. Most meetings were done on a Thursday but depending on members availability, the meeting can be change to another day of the week.

### Sprint Demo
* How did this apply? <br />
At the end of each sprint, we ran the program and test if the application was working as intended through the use of the acceptance tests for each story. If the acceptance test passed, then the user story was completed. The acceptance tests were ran automatically and manual for each sprint. These tests involve running the program and checking if the normal, alternative and error flow for the story was respected. We also check if the application would crash as well. The tasks that couldn’t perform the acceptance tests, needed to be reviewed and implemented to pass.

* How did this evolve? <br />
For both sprints, we ran the acceptance tests to make sure all user stories were completed. However, there was also a demo with the teacher assistance for a demo of the entire application. Additional testing for corner cases and potential crashed were done to allow the teacher assistant to perform any necessary operations to demonstrate a working android application with all high priority user stories covered. All user stories and fixes were completed before this demo happened.

### Sprint Retrospective
* `Julien`

## Scrum Objects
* (how did this apply/evolve)
### Done Checklist
* How did this apply? <br />
For each sprint, the checklist was performed to verify the status of the sprint. For each task in the sprint, the code was implemented and a pull request with the changes was done. Each pull request ran an automatic Travis CI build and code reviewed was necessary. If the build doesn’t fail and automatic tests pass then changes can be merged to the remote repository. Additional unit tests can be included with the code changes to increase test coverage. After each item was reviewed in the sprint, we checked if all story acceptance test passed and previous one still worked. The test coverage is checked to determined if it still the same or higher than previous sprints.

* How did this evolve? <br />
Throughout the project, a lot of tasks were done towards the end of the sprint. Some unit tests were not all included each task. However, acceptance tests were performed to confirm if the application still works. Some of the corner cases were not all completed for each sprint but the test coverage was relatively higher for each sprint.
### Product Backlog
* `Patrick`
### Sprint Backlog
* `Patrick`
### Sprint Backlog Tasks
* How did this apply? <br />
In the sprint planning, we split the user stories for the sprint into multiple tasks. Depending on the user stories, there would be programming, testing or designing tasks. Each of the tasks were marked with different tags for team members to pick. The tasks were created from splitting the stories into small piece of work for each story. After splitting each user stories into tasks, we wrote the task into a sprint task list excel sheet. Each member can pick their own tasks they want to work on. Members working on a task for a user stories had to communicate with the other members working on the same user stories. Each task was picked by one member and executed by them.

* How did this evolve? <br />
Throughout the project, the approach stayed the same. The only difference is some tasks needed contribution from other members. These tasks picked from one member, but the contribution can be given to multiple people. Some of the tasks picked up from the members can be given to other members that had relative skills to complete the task. The previous member would pick another one that was aligned to their skill set.

### Story Point Burndown Chart
* `Patrick`

## Agile Manifesto
* (talk about application to project and evolution)
### *"Individuals and interactions over processes and tools"*
* `Samuel`
### *"Working software over comprehensive documentation"*
* `Wiam`
### *"Customer collab over contract negotiation"*
* `Samuel`
### *"Responding to change over following a plan"*
* `Luke`

## Agile Principles
### *"Our highest priority is to satisfy the customer through early and continuous delivery of valuable software"*
* `Samuel`
### *"Welcome changing requirements, even late in development, Agile processes harness change for the customer's competitive advantage"*
* `Luke`
### *"Deliver working software frequently, from a couple of weeks to a couple of months, with a preference to the shorter timescale"*
* `Ben`
### *"Business people and developers must work together daily throughout the project"*
* `Wiam`
### *"Build projects around motivated individuals. Give them the environment and support they need, and trust them to get the job done"*
* `Wiam`
### *"The most efficient and effective method of conveying information to and within a development team is face-to-face conversation"*
* `Samuel`
### *"Working software is the primary measure of progress"*
* `Julien`
### *"Agile processes promote sustainable development. The sponsors, developers, and users should be able to maintain a constant pace indefinitely"*
* `Julien`
### *"Continuous attention to technical excellence and good design enhances agility"*
* `Jiawei`
### *"Simplicity - the art of maximizing the amount of work not done - is essential"*
* `Jiawei`
### *"The best architectures, requirements, and designs emerge from self-organizing teams"*
* `Shi Yu`
### *"At regular intervals, the team reflects on how to become more effective, then tunes and adjusts its behaviour accordingly"*
* `Samuel`

## Conclusion
### Scenarios The Project Is Not Suitable For
For this project, we implemented only the high priority stories. This is because they are the core functionalities which encompass a minimum viable product in terms of what we want our application to accomplish. Medium/low priority stories were not assigned for our sprints due to time constraints. Notably, these lower priority stories include additional admin activities, and ways for users to communicate with admins, and ultimately other users, labeled as feedback in the stories, among other minor features. As such, presently the application is standalone and functions locally only, and new features would come in newer versions of the application, with the current configuration of the project. This also means that the application is not appropriately implemented to scale up and handle a large amount of users, as this was a low priority in the scope of this project.

In this project, a low amount of testing was done, in favour of having a mostly functioning build. User input validation and sanitization is not part of our testing suite at present. Also, acceptance testing was not automatically implemented. This was primarily due to time constraints, where we focused on high priority stories instead, and having a working application for the demo. After the unexpected amount of time that was spent in assignment B, it was deemed that too much time would be spent working on implementing thorough acceptance testing. Automatic acceptance testing was made to be a lower priority for implementation, to come after the high priority stories were implemented.

* Untested Corner Cases <br />
Most user inputs are not thoroughly validated and sanitized in the present implementation of the project. For example, when the user inputs the number of exercises for a muscle group that he/she would like to generate, if the input number contains to many digits (say, nine or above), then the application will stall and crash. This extends to the corner case when the user instead checkmarks every muscle group and asks for a 6-digit amount of exercises for each, in which case the application will also stall and crash. There are other cases demonstrating the lack of input validation, however these are mostly examples of edge cases. Considering the application is still relatively simple at this stage, there are less variables at play which could then constitute corner cases.

* The UI/UX paths that you didn't test `Hank`
    - Ideally there should only be one way to do something `Hank`

### Features Not Implemented Due to Time Limits, and Possible Extensions to Our Project
As mentioned earlier, this project only implemented the high priority stories and forewent medium/low priority stories, including thorough testing suites. Implementing these lower priority features was not required by the project, but would be implemented if more time was given.
    
In addition, the database that was used in this implementation, SQLite, is a local database. This implementation is a far simpler one compared to the originally planned database, which would use a remotely hosted database running PostgreSQL. This remote database was nearly fully implemented, but it was decided we would switch to a local implementation after issues with connecting to the database and the time crunch. Since the database is required in many functions within the project, we were forced to use an alternative. While the SQLite database fulfills all the high priority stories adequately, it would fails the medium and low priority stories that would require communication between users. Ultimately, the current local database would be replaced with a remote database. Whether this theoretical implementation would be a PostgreSQL or MySQL database or some other alternative was never finalized. Due to the modular implementation of the current database with an interface (see DataSource.java) for the database, this upgrade would be possible without rewriting the entire codebase.

## References
* If needed

## Appendix
* If needed
