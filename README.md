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
Brian Kim-Lim | 260636766 |
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
The source code to develop the application includes multiple different files, many of which interact with other files and serve a certain use. For the purpose of coherently explaining the architecture, this section is split up into the different paths of the source code, and each path details how the files are utilized when running the application.

### _/app/src/main/java/mcgill/shredit/_
Files included in this exact path are used to control the various UI views and obtain any releveant data for a given view. Each class has a corresponding xml file in _"src/main/res/layout/"_. Each xml file serves as the actual design of UI elements for that view, and additional xml files are also included for various popup dialogues needed within the app. The xml files are organized by the _"/app/src/main/AndroidManifest.xml"_ file, which dictates the initial view and relates all views with their corresponding Activity class.

* _LoginActivity.java_<br/>
This class controls the login page that is first presented to the user when the app opens. It includes a function to authenticate the entered username and password, and a function to handle signups. When the login button is pressed, the system queries for the list of users in the database and checks if the username password is correct. If it succeeds, the system is directed to _HomeActivity.java_ and the user is logged in. If it fails, an error prompt is shown. When the signup button is pressed, the system queries the list of users in the database and checks if the username already exists. If the username does not exist, the user is signed up with the entered username and password. If the username already exists, an error prompt is shown.<br/>

The login activity also facilitates admin users. By adding the string "@admin" to the end of a username, an admin account will be created when the signup button is pressed. If an admin username is entered and the login succeeds, the system is directed to _AdminHomeActivity.java_ instead.

* _HomeActivity.java_ <br/>
This class controls the landing page for gym user . The user is presented with the following buttons to proceed with generating a workout:
	- "Search Gyms" directs the system to _GymActivity.java_
	- "Customize Gym" directs the system to _CustomizeGymActivity.java_
	- "Load Workout" directs the system to _SavedWorkoutActivity.java_
	- "Log Out" directs the system to _"LoginActivity.java"_

* _GymActivity.java_ <br/>
This class shows a list of preset Gyms by querying the database. If the user finds the gym they are working out at, pressing the list item will select the gym and direct the system to _GymPresetActivity.java_.

* _GymPresetActivity.java_ <br/>
This class shows the equipment available at the currently selected gym. When the activity is loaded, the system gets the Gym object passed from _GymActivity.java_ and stores it. The list is then created by getting the list of equipment from the Gym object. If the user is happy with the currently selected gym and presses the confirm button, the system is directed to _MuscleGroupActivity.java_.

* _MuscleGroupActivity.java_ <br/>
This class controls the muscle group selection activity. A list of muscle groups is generated from the values in _MuscleGroup.java_, with each list item having their own checkbox and input field for the number of exercises. The user can pick the muscle groups they would like to work on by pressing the checkbox. The user can also indicate how many exercises they would like for each muscle group. Once the user is happy with their selection, pressing the ok button will direct the system to _WorkoutActivty.java_. If no muscle groups are selected or all inputs indicate zero exercises, an error prompt is shown.

* _WorkoutActivity.java_ <br/>
This class is the main workout activity that lists the exercises generated from the selected MuscleGroups and Gym. When the activity is loaded, the system queries the database for the list of possible exercises, and filters them by the selected muscle groups. The system then picks a random exercise for each muscle group until the selected number of exercises for each muscle is satisfied. A workout object is created, and the list of exercises is displayed. If the user presses an exercise in the list, the system redirects to _WorkoutSwapPopupActivity.java_. If the user presses the save workout button, an alert is displayed requesting a unique name. If the entered name contains invalid characters or is not unique in the database, an error prompt is shown and the alert is closed. If the entered name is valid and unique, the workout object is saved in the database under the entered workout name. Once the user is completed their workout, pressing the home button will redirect the system to _HomeActivity.java_.

* _WorkoutSwapPopupActivity.java_ <br/>
This class controls the popup that is created when an exercise is selected in _WorkoutActivity.java_. This class allows the user to select a different exercises to swap with the currently selected exercise. The first list item is "Random Exercise", which will swap the current exercise with a random exercise in the list of all exercises. The user may also scroll through the rest of the list to pick an exercise to swap with the current exercise. Once an exercise is selected, the popup is closed, and the system is directed back to _WorkoutActivity.java_ with an updated exercise list. If the user changes their mind, pressing the cancel button will close the popup.

* _CustomizeGymActivity.java_ <br/>
This class controls the custom gym creation activity. This activity is useful for a user that cannot find their gym in the list of presets, or a user that works out at home. A list of workout equipment is displayed by querying the database. The user can select one or more gym equipments depending on their current gym setup. Once the user is satisfied with their selection, pressing the submit equipment button will direct the system to _MuscleGroupActivity.java_.

* _SavedWorkoutActivity.java_ <br/>
This class displays the list of workouts saved in the database and allows the user to load or delete a selected workout. If there are no currently saved workouts, an error dialog is shown, and the system is redirected to _HomeActivity.java_. When the user selects a workout in the list, the list item is highlighted. If the user presses the load button, the system is directed to _WorkoutActivity.java_ and the selected workout is loaded. If the user presses the delete button, an alert is shown confirming the operation. Once confirmed, the selected workout will be deleted from the list and on the database. If no item is selected when the load or delete button is pressed, an error prompt is shown.

* _AdminHomeActivity.java_ <br/>
This class controls the landing page for the admin. The admin is presented with the same buttons in _HomeActivity.java_ with the addition of a "Modify Exercise" button. If the admin presses the "Modify Exercise" button, the system is directed to _AdminModifyExerciseActivity.java_.

* _AdminModifyExerciseActivity.java_ <br/>
This class displays a list of all the exercises in the database for the admin to modify. If the admin selects an item and presses the delete button, the exercise is removed from the list and on the database. If the admin presses the add button, the system is redirected to _AdminAddNewExerciseActivity.java_. Once the admin is finished modifying exercises, pressing the done button will return the system to _HomeActivity.java_.

* _AdminAddNewExerciseActivity.java_ <br/>
This class controls the popup created when an admin wishes to add a new exercise. The popup allows the admin to enter a unique exercise name, enter the equipment associated with the exercise, and select the muscle group the exercise works on. Once the admin is satisfied, pressing the done button will attempt to add the exercise. If the exercise name is valid and unique and all fields have been filled, the exercise will be added to the database and the popup closes. If fields are missing or the exercise name is invalid, an error prompt is shown.

### _/app/src/main/java/mcgill/shredit/model/_
Files included in this path are the data objects that are utilize within the application. These files were generated from an umple file located at _"/app/ShreditModel.ump"_ which describes the application model. The classes and their attributes/associations are listed as follows:

* _User.java_ <br/>
	- username
	- password

* _Gym.java_ <br/>
	- name
	- equipments

* _Equipment.java_ <br/>
	- name

* _Exercise.java_ <br/>
	- name
	- description
	- muscleGroup
	- equipment

* _Workout.java_ <br/>
	- name
	- exercises

### _/app/src/main/java/mcgill/shredit/data/_
The files included in this path handle the reading and writing of data for our database.

* _DataSourceLite.java_ <br/>
This class initializes an SQLite database on the first run of the application on a device. Subsequent runs of the application will utilize existing database instance to process incoming queries. All queries made in each application activity will call a function in this class to read/write to the database.

* _Repository.java_ <br/>
This class loads _DataSourceLite.java_ given the context of the activity that instantiated this class. Each activity creates an instance of Repository with the "getInstance()" function. Initially the repository was intended to serve as a singleton class that controls a PostgreSQL database. However given that PostgreSQL was not directly compatible with Android Studio due to security limitations, SQLite was used instead.

* _DataSourceStub.java_ <br/>
In order complete and test front-end tasks throughout the project, this stub was created to mock the database functions that were not implemented yet. This allowed test-driven development to be continued on the application activities, while the back-end systems were still under development.

### _src/main/assets/_
Here we store the initial datatables for our application. Each file is in csv format and represent a specific entity that should be stored in the database. On the first run of the application, _DataSourceLite.java_ runs an initial function to create an SQLite database from the csv files stored in this path. Subsequent runs of the application will read and write to the existing database instead of creating a new one. The csv files and column names are listed as follows:

* _equipment.csv_
	- "eq_name"
* _exercises.csv_
	- "ex_name"
	- "description"
	- "musclegroup"
	- "eq_name"
* _gymequipment.csv_
	- "g_name"
	- "username"
	- "eq_name"
* _gyms.csv_
	- "g_name"
	- "username"
* _users.csv_
	- "username"
	- "password"
* _workoutexercises.csv_
	- "w_name"
	- "username"
	- "ex_name"
* _workouts.csv_
	- "w_name"
	- "username"

## Running/Testing the Application
* Does it explain how to run and test your project `Hank`

## Scrum Rituals
### Backlog Grooming
* How this apply? <br />
After forming the group, a meeting was done to discuss about the project and ideas for the android application. After the initial discussion and ideas of the workout application, we created user stories for the application in the product backlog based on the gym workout generating ideas. Many user stories were created for the product backlog. We decided to mark each of these user stories with high, medium or low priorities. We only kept the high priorities stories to create a functional application and functional requirement needed to implement these user stories. We also written acceptance tests related to the high priority stories to have better understanding of the requirements of the story.

* How this evolve? <br />
When we worked on the application, some of the user stories needed to be revised after discovering additional details to these stories. The user stories were adjusted based on new details or user stories that weren’t there. We added and adjusted the product backlog for some user stories and added clarification to the user stories in the product backlog.

### Sprint Planning
* How this apply? <br />
Before each sprint, we set up a meeting to go through the product backlog and decide which user stories to accomplish for that sprint. Since we decided to only have 11 high priority user stories in the product backlog, we planned 6 user stories to be completed on the first sprint and 5 on the second sprint. The first sprint’s user stories focused on making a barebone android application that can log a user and generate a work. Also, the first sprint was to setup a database and initialize views for the application. The second sprint involved 5 user stories which new functionalities or refined sprint 1 functionality.

* How this evolve? <br />
Based on the first sprint, some of the user stories could not be completed because of functional requirements that blocked these stories completion. On our second sprint, we decided to push the incomplete user stories with the sprint two stories. Some of these incomplete user stories were the base for some of the sprint 2 tasks. In the second sprint planning, we put more tasks to be done to complete the project by including the incomplete stories.

### Story Estimation
* Initial Estimations <br />
Initial Story estimations were intentionally over-estimations. Each story was split into several key component that would be considered as tasks. Specifically these were coding, data, testing, design of views, functional requirements, and management tasks. Some tasks would be shared between stories, such as the login and sign-up stories would share a view. Once these tasks were formed, a generous estimation was made of how long each of these tasks would take. This approach was fairly effective, but not without flaws. In some cases the time to complete a task was over-estimated and sometimes underestimated by several hours.

* Revising Estimations <br />
The issue with the previous system of story estimation was that the amount of estimated work was off by several hours frequently, and as such people would be working an inconsistent amount of time. Estimations got more accurate since developers become more familiar with the codebase, but it was also understood that mistakes would still be made. At the beginning of the second sprint, each of the stories that pulled over from the first sprint, and the stories from the second sprint were brought together and time estimates were remade. For the most part these would be more accurate to actual time spent. In addition, not all tasks were immediately assigned for sprint 2, to allow people with unexpectedly short initial tasks to be able to achieve the required hours for their work. By week 3 of sprint 2, all tasks were properly assigned, with the best possible time estimation.

### Daily Stand-up Meeting
* How this apply? <br />
Because of scheduling issues and difficulty of gathering 14 members for a daily meeting, we decided that team members can update daily their task through online methods. However, there was a weekly meeting set up to gather the most number of members to discuss and clarify the tasks and progression of the project. In these meeting, members can communicate face to face or through voice chat their concerns, progression and upcoming task.

* How this evolve? <br />
This stays reasonably the same throughout the entire project since team members were occupied with different classes and other commitments. The weekly meeting helped make sure clarification and update on the sprint planning were done. The daily online messaging helped members communicate their intentions and collaborate in different tasks. Most meetings were done on a Thursday but depending on members availability, the meeting can be change to another day of the week.

### Sprint Demo
* How this apply? <br />
At the end of each sprint, we ran the program and test if the application was working as intended through the use of the acceptance tests for each story. If the acceptance test passed, then the user story was completed. The acceptance tests were ran automatically and manual for each sprint. These tests involve running the program and checking if the normal, alternative and error flow for the story was respected. We also check if the application would crash as well. The tasks that couldn’t perform the acceptance tests, needed to be reviewed and implemented to pass.

* How this evolve? <br />
For both sprints, we ran the acceptance tests to make sure all user stories were completed. However, there was also a demo with the teacher assistance for a demo of the entire application. Additional testing for corner cases and potential crashed were done to allow the teacher assistant to perform any necessary operations to demonstrate a working android application with all high priority user stories covered. All user stories and fixes were completed before this demo happened.

### Sprint Retrospective
* `Julien`

## Scrum Objects
* (how did this apply/evolve)
### Done Checklist
* how this apply? <br />
For each sprint, the checklist was performed to verify the status of the sprint. For each task in the sprint, the code was implemented and a pull request with the changes was done. Each pull request ran an automatic Travis CI build and code reviewed was necessary. If the build doesn’t fail and automatic tests pass then changes can be merged to the remote repository. Additional unit tests can be included with the code changes to increase test coverage. After each item was reviewed in the sprint, we checked if all story acceptance test passed and previous one still worked. The test coverage is checked to determined if it still the same or higher than previous sprints.

* how this evolve? <br />
Throughout the project, a lot of tasks were done towards the end of the sprint. Some unit tests were not all included each task. However, acceptance tests were performed to confirm if the application still works. Some of the corner cases were not all completed for each sprint but the test coverage was relatively higher for each sprint.
### Product Backlog
* `Patrick`
### Sprint Backlog
* `Patrick`
### Sprint Backlog Tasks
* how this apply? <br />
In the sprint planning, we split the user stories for the sprint into multiple tasks. Depending on the user stories, there would be programming, testing or designing tasks. Each of the tasks were marked with different tags for team members to pick. The tasks were created from splitting the stories into small piece of work for each story. After splitting each user stories into tasks, we wrote the task into a sprint task list excel sheet. Each member can pick their own tasks they want to work on. Members working on a task for a user stories had to communicate with the other members working on the same user stories. Each task was picked by one member and executed by them.

* how this evolve? <br />
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
* How this applies?<br/>
We organized our initial planning to adhere to this philosophy by picking tasks to complete a certain number of hours per week over the span of two months. This allowed us to continuously have topics to discuss during the weekly meetings. Another major benefit was that everyone was on the same page as to which tasks were currently complete and which tasks need to be completed before other tasks could be started. Since everyone was ideally working each week, we resolved any conflicts in the moment instead of dragging out conflicts, allowing a shorter timescale. Tasks were also broken down as small as possible. This made sure that each task required small additions of code, which allowed more frequent pull requests to the master branch. We also setup Travis-CI for continuous integration, which would help us validate any software changes made by a developer to ensure that the master branch is always a working application that is free of bugs.


* How this evolved?<br/>
As the sprints for our project went on, we still succeded in following this agile principle. Our initial plan was hindered by the many different commitments that our team had to other classes and projects. Many tasks were delayed due to varying work schedules and other commitments until the last week of the final sprint. However given the delays, the tasks developed went unchanged. Our team made sure to commit and push code frequently, with Travis-CI and unit tests checking if the software works as intended.

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
* `separate these into paragraphs yourselves`
* what scenarios is the project not suitable for `Brian`
* This is where you explain what you didn't implement or test `Brian`
* The corner cases that you didn't test `Brian`
* The UI/UX paths that you didn't test
    - Ideally there should only be one way to do something `Hank`
* What feature are you not implementing due to time limit or limitations
* Possible extensions to your project
    - This project only implemented the high priority stories. As such, many features would be implemented in future releases. Medium/low priority stories notably include additional admin activities, and ways for users to communicate with admins, and ultimately other users, labeled as feedback in the stories, among other minor features. Implementing these features was not required by the project, but would be implemented if more time was given.
    - In addition, the database that was used in this implementation, SQLite, is a local database. This implementation is a far simpler one to the originally planned DB, which would use of a remotely hosted database running PostgreSQL. This remote database was partially implemented but it was decided to switch to a local implementation after issues with connection and the time crunch, the database being required in many functions within the project. While the SQLite database fulfills all the high priority stories adequately, it would fails the medium and low priority stories that would require communication between users. Ultimately the current local database would be replaced with a remote database. Whether this theoretical implementation would be a PostgreSQL or MySQL database or some other alternative was never finalized. Due to the modular implementation of the current database with an interface (see DataSource.java) for the database, this upgrade would be possible without rewriting the entire codebase.
    - In this project acceptance testing was not automatically implemented. This was primarily due to a time constraint. After the unexpected amount of time that was spent in assignment B, it was deemed that too much time would be spent working on implementing it. Automatic acceptance testing was made to be a lower priority for implementation, to come after the high priority stories were implemented.

## References
* If needed

## Appendix
* If needed
