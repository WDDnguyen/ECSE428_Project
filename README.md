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
Shi Yu Liu | 260683360 | @shiyu-mochi
Jiawei Ni | 260615965 |@jiaweini
Wiam El Ouadi | 260663710 |
Aidan Sullivan | 260733921 | @aidssmcc
Patrick Ghazal | 260672468 | @PatrickGhazal
Brian Kim-Lim | 260636766 | @briankimlim
Samuel Genois | 260692287 |
Julien Courbebaisse | 260614548 | @allockicmoi
Ebou Jobe | 260664278 |
Luke Ma | 260745824 | @lukewma
Volen Mihaylov | 260746982 |
Hank Zhang | 260684347 |

## Project Motivation
* Motivation/Reason for why the project should exist in the world.  `Luke`
* Make sure you explain what your project is and what problem it is solving. Why was the project needed: is it solving a problem not solved by other products? is it improving over the existing products?

Shredit is an app designed for people who want to go to the gym and really enrich their experience by diversifying their workouts. This could be a seasoned gym goer who's looking for more variety in their workouts, or it could be a newbie who's overwhelemed by all of the options out there and needs the details to be taken care of for them, or event a personal trainer who's looking for some inspiration for a client. It is an app that is flexible, lightweight, and user centric. There are exercise databases out there, but no apps that allow for the customizability and variety that ours does. Shredit allows the user to base their workouts around the muscles they want to target, and will inject a sense of novelty into your workouts, preventing them from getting stale or repetitive.

## Design Choices
We chose to implement our ShredIt idea into an mobile application, as we wanted the project to be mobile and portable, so that people can easily access it during their workouts at the gym. Since most of our team members were familiar with java and android development, it was then an easy choice to choose Android Studio to develop this project. Steaming from this decision, the coding style then adhered to the Google’s Java Style Guide and the Code Conventions for the Java TM Programming Language we have learned from prior classes.

The Espresso test framework was chosen for android interface testing for our acceptances tests due to its ability to start the test on select activities and integration via JUnit testing. This meant that knowledge from prior class could be reused, and it cuts down test time for tests unrelated to the main activity.

Github was used to share the code among all our team members, as it has inbuilt version control via Git. From this decision, the use of Travis CI for continuous integration purposes was then used due to its ease of integration to GitHub.
The database portion of the project was first decided to be implemented via PostgreSQL, but it was actually implemented via SQLite, as it was the easiest alternative to implement and the most familiar to the team member that fulfilled the task. The section “Features Not Implemented Due to Time Limits, and Possible Extensions to Our Project” goes into more details as to why this decision was made.

Task distribution was done on a scrum basis: first come first serve, people take tasks that they are confident their will finish. However, when people fail to finish their task due to unforeseen circumstances, they are then redistributed to someone to offers to complete it. At first, the task board was made via trello, however due to submission purposes, it was migrated into an excel spreadsheet for submission purposes. This trades off visualisation for distribution.

To coordinate the team members, Google Drive was used to share documents and Slack was used in the beginning for communication, but we migrated towards Messenger as it was more accessible and more responsive.

## Use of External Technology
* References/Links to every technology that you didn't create yourself (i.e. frameworks, IDEs, version control, issue tracker, cloud hosting platform) 

Github: we used Github as a VCS service and as an issue tracker. Although we did not have that much in terms of tasks to complete, and with the format of the sprints, we did not have very specific tasks as we normally might have, we used it as a way to review any of the code being pushed or merged into master and to keep each other accountable with pull requests.
Android Studio: we used Android Studio as an IDE to test and build our app, and because not everybody owned a Mac to build in swift.
AWS: We initially used AWS to host a mySQL database, then changed it to PostgreSQL, before deciding to host it locally. We also had set up a web server to connect with the database, but had difficulties with the connection, so scrapped that in favor of using an SDBC driver, which didn't work finally. 
Espresso: we used Espresso to help test the Android app's UI.
Slack: we had initially set up a slack for communication, but it didn't stay because not everyone was using it regularly
Facebook Messenger: we used facebook messenger when we first formed the team and to find everyone, but it ended up becoming our defacto method of communication
Google Docs: we used Google Docs as a way to write out any documentation that we had to do, such as the project preparation document and for our task list.
Umple: we used Umple as a way to set up the architecture of our code and to visualize it better, and also to use it to automatically generate some boilerplate code. 
PostgreSQL: we had used a postgreSQL database at first, because it seemed easiest to use and because the client was much more lightweight than the mySQL one, but the connections didn't work, so we switched to SQLite
SQLite: we hosted a local version of the database within our app with SQLite.
PGAdmin: was our PostgreSQL client, used it to set everything up.
Travis CI: we used Travis to ensure that no PRs would break anything, mostly by way of basic JUnit tests and simple acceptance testing. 
trello: we used initially used trello to keep a task list, before changing to an excel document per the TA's request.


## Software Architecture
The source code to develop the application includes multiple different files, many of which interact with other files and serve a certain use. For the purpose of coherently explaining the architecture, this section is split up into the different paths of the source code, and each path details how the files are utilized when running the application.

### _/app/src/main/java/mcgill/shredit/_
Files included in this exact path are used to control the various UI views and obtain any releveant data for a given view. Each class has a corresponding xml file in _"src/main/res/layout/"_. Each xml file serves as the actual design of UI elements for that view, and additional xml files are also included for various popup dialogues needed within the app. The xml files are organized by the _"/app/src/main/AndroidManifest.xml"_ file, which dictates the initial view and relates all views with their corresponding Activity class.

* _LoginActivity.java_<br/>
This class controls the login page that is first presented to the user when the app opens. It includes a function to authenticate the entered username and password, and a function to handle signups. When the login button is pressed, the system queries for the list of users in the database and checks if the username and password is correct. If it succeeds, the system is directed to _HomeActivity.java_ and the user is logged in. If it fails, an error prompt is shown. When the signup button is pressed, the system queries the list of users in the database and checks if the username already exists. If the username does not exist, the user is signed up with the entered username and password. If the username already exists, an error prompt is shown.

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

* _User.java_
	- username
	- password

* _Gym.java_
	- name
	- equipments

* _Equipment.java_
	- name

* _Exercise.java_
	- name
	- description
	- muscleGroup
	- equipment

* _Workout.java_
	- name
	- exercises

### _/app/src/main/java/mcgill/shredit/data/_
The files included in this path handle the reading and writing of data for our database.

* _DataSourceLite.java_ <br/>
This class initializes an SQLite database on the first run of the application on a device. Subsequent runs of the application will utilize existing database instance to process incoming queries. All queries made in each application activity will call a function in this class to read/write to the database.

* _Repository.java_ <br/>
This class loads _DataSourceLite.java_ given the context of the activity that instantiated this class. Each activity creates an instance of Repository with the "getInstance()" function. Initially the repository was intended to serve as a singleton class that controls a PostgreSQL database. However given that PostgreSQL was not directly compatible with Android Studio due to security limitations, SQLite was used instead.

* _DataSourceStub.java_ <br/>
In order to complete and test front-end tasks throughout the project, this stub was created to mock the database functions that were not implemented yet. This allowed test-driven development to be continued on the application activities, while the back-end systems were still under development.

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
* How did this apply? <br />
At the end of each sprint, the team collectively checked that tasks for the sprint were completed and that relevant tests passed. From this analysis, the team was able to identify key stress points to work on, which ultimately allowed us to ship a fully functional product.

* How did this evolve? <br />
Both sprints turned out to be well planned and well executed. In retrospect some improvements could have been made. In particular, a task should have been to research and identify the best way to implement a persistent database. Failing to recognize the high priority of this task caused some delays in project development as well as last minute fixes. Overall though the other high priority requirements were well identified and included in sprint 1 which in the end allowed for flexibility when unseen difficulties emerged.

## Scrum Objects
* (how did this apply/evolve)
### Done Checklist
* How did this apply? <br />
For each sprint, the checklist was performed to verify the status of the sprint. For each task in the sprint, the code was implemented and a pull request with the changes was done. Each pull request ran an automatic Travis CI build and code reviewed was necessary. If the build doesn’t fail and automatic tests pass then changes can be merged to the remote repository. Additional unit tests can be included with the code changes to increase test coverage. After each item was reviewed in the sprint, we checked if all story acceptance test passed and previous one still worked. The test coverage is checked to determined if it still the same or higher than previous sprints.

* How did this evolve? <br />
Throughout the project, a lot of tasks were done towards the end of the sprint. Some unit tests were not all included each task. However, acceptance tests were performed to confirm if the application still works. Some of the corner cases were not all completed for each sprint but the test coverage was relatively higher for each sprint.
### Product Backlog
* How did this apply? <br />
The Product Backlog was an essential element of our project. During our first group meeting, after having decided our app, we had to determine the three components of the Backlog: actors, user stories and requirements.
Determining the actors was a straightforward process: the primary users would be regular gym-goers, and the secondary users were the administrators/moderators, whose role would be to handle existing database content: creating pre-sets for existing gyms, modifying the exercise list, etc.
Having found our users, we needed to write user stories for them, separating them in high-, medium-, and low-priority. 23 stories were written, 11 of which were categorized as high-priority. Given that the high-priority stories were the only ones to be implemented, we made sure to select stories that would cover varied uses of the app and that were relevant and necessary to its proper functioning. 
Finally, we wrote our requirements. We decided to include only database requirements in our high-priority section, and left the more trivial specifications (allowing password changes, server timeout in case of inactivity…) to the medium and low-priority requirements.

* How did this evolve? <br />
The Product Backlog did not significantly change or evolve after the first specification. Trivially, the actors did not change, and neither did the requirements, given that the requirements were our own and were set in stone after the first meeting. More time was spent on the user stories, but even they were finalized quickly, which allowed us to move on to the Sprint Backlog.
### Sprint Backlog
* How did this apply? <br />
Before building the sprint backlogs, we had to extract a series of tasks for each user story. Approximately 3-5 tasks were established for each story, divided in 4 sections:
•	Data (generating mock data)
•	Programming (writing the code for the app’s behaviour)
•	Testing (writing tests for the code of the Programming section)
•	Views (designing the Android views)
A fifth section, Management, corresponded to the Scrum Master’s responsibilities. Finally, functional requirements were also added as tasks. 
The next step was to determine which tasks would be assigned to each Sprint. To do so, we selected a subset of tasks that were critical for the proper functioning of the application (e.g. sign up, workout generation) and assigned them to Sprint 1, leaving the remaining tasks to Sprint 2.
The Sprint Backlog was implemented as a shared Google Sheets document, allowing any group member to update their progress at any time. The Backlog consisted of the following entries for each task:
•	Sprint number
•	Task name
•	Task story
•	Team member name/ID
•	Start/end dates
•	Estimated/actual time spent on the task

* How did this evolve? <br />
Once determined, the backlog for Sprint 1 did not significantly evolve. The work was evenly split among team members, which allowed for each member to work at their own pace (notwithstanding cases where one member’s work was required for another to do theirs). The Sprint 2 backlog was thus quite straightforward: whatever had not been done in Sprint 1. That explains why some Sprint 1 tasks found themselves in Sprint 2 as well (some of the requirements for example).

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
Our team is composed of 14 individuals with different experience and expertise regarding software development. Relying on processes and tools to guide our development process was impossible as their where little such processes and tools that every member of the team was familiar with. Moreover, development processes typically require regularity as to how the workload is tackled, which is ill suited for this student project, where all 14 of had drastically different, irregular and often unpredictable schedules. Instead, our development process relied heavily on our weekly face-to-face team meetings and our regular communications on our chat group.

### *"Working software over comprehensive documentation"*
* The team prioritized a fully functional software over documentation throughout the semester. The team recognized that a working application and a less detailed documentation would be better than the opposite. 

### *"Customer collaboration over contract negotiation"*
We chose as a project to develop an application for gym users with unique features meant to enhance their workout experience. The idea stemmed from the desire of one of our team members who expressed his wish to use such an application himself for his own benefit. However, most of the other member of the team had little to no experience with regular exercising at a gym, and thus were on their own, unable to fully understand what kinds of features regular gym users wound potentially need, want or expect from an application such as the one we developed. Thus, both at the beginning of the project and throughout development, it was imperative that we regularly consult with the team member that originally envisioned our product - who served, for all intents and purposes, as our teams’ product owner and customer – remains true to his original vision, as our product’s value rests solely on the promise that he, and other gym users like him, will use it. We strongly believe that opting for a formal documented prescription of our customer’s needs instead of regular, face-to-face consultation would have resulted in multiple misinterpretations of our customers’ needs, which in turn would have resulted in the development of a final product or lesser value.
### *"Responding to change over following a plan"*
A great example of a change that we came to is the realization in our 2nd sprint that we would not be able to finish our medium and low priority stories in time for this deliverable, which is why we decided to scrap them and focus on the high priority ones for this second sprint, in order to come out with a proper working product. At the end of the day, even that was difficult but we were very proud to see it carried through.

## Agile Principles
### *"Our highest priority is to satisfy the customer through early and continuous delivery of valuable software"*
As previously stated above in our detailing of our compliance to the “Customer collaboration over contract negotiation” value of the Agile Manifesto, the success of our project in producing an application valuable to our customer greatly depended on our ability to discuss the customer’s needs with him regularly. Together with discussion of feature descriptions and user stories, our best method for obtaining regular customer feedback was to produce functioning, useful and demonstratable prototypes to our product owner. Whether it was using Android Studios’ Android Emulator and Espresso based automated tests for user interface related features, or plain JUnit automated tests of Android independent processes such as the data persistence related features, every instances of our codebase that successfully compiled was also executable, and its execution. As such, it was always possible to demonstrate the most recent progress in our application’s development to our product owner for him to provide feedback, and we used this advantage to obtain this crucial feedback early and frequent during development.

### *"Welcome changing requirements, even late in development, Agile processes harness change for the customer's competitive advantage"*
* `Luke`


### *"Deliver working software frequently, from a couple of weeks to a couple of months, with a preference to the shorter timescale"*
We organized our initial planning to adhere to this philosophy by picking tasks to complete a certain number of hours per week over the span of two months. This allowed us to continuously have topics to discuss during the weekly meetings. Another major benefit was that everyone was on the same page as to which tasks were currently complete and which tasks need to be completed before other tasks could be started. Since everyone was ideally working each week, we resolved any conflicts in the moment instead of dragging out conflicts, allowing a shorter timescale. Tasks were also broken down as small as possible. This made sure that each task required small additions of code, which allowed more frequent pull requests to the master branch. We also setup Travis-CI for continuous integration, which would help us validate any software changes made by a developer to ensure that the master branch is always a working application that is free of bugs. As the sprints for our project went on, our initial plan was hindered by the many different commitments that our team had to other classes and projects. Many tasks were delayed due to varying work schedules and other commitments until the last week of the final sprint. However given the delays, the proposed tasks went unchanged. Our team made sure to commit and push code frequently, with Travis-CI and unit tests checking if the software works as intended.

### *"Business people and developers must work together daily throughout the project"*
* Not applicable 
### *"Build projects around motivated individuals. Give them the environment and support they need, and trust them to get the job done"*
* The team was managed in a way that was not stressful. Team members could chose the tasks that they wanted to perform and whenever a problem arised, the rest of the team would always help each other to solve the issue. 
### *"The most efficient and effective method of conveying information to and within a development team is face-to-face conversation"*
There was no collective understanding of any convention that we could have used as a shorthand to speed up collaboration. Our best option was to ensure a deepened mutual understanding of our project’s goals, tasks and the progress of each individual member’s contribution through face-to-face dialog. This was achieved with the planning of weekly team meetings. Understandably, it was impossible for all 14 members to physically attend every meeting, yet member that could not made the commitment to participate remotely. Of course, we had other means of communication. For instance, we also extensively used a Messenger chat group, which we could use more frequently and which did not require any time commitment. However, in our experience, throughout this project our in-person meetings were much more reliable and effective at resolving misunderstandings, sharing information on each other’s project, discussing features and user stories, and defining and distributing tasks.

### *"Working software is the primary measure of progress"*
The team's main objective was to ship a fully functional application in terms of our High-Priority User Stories. In that respect the team successfully accomplished this goal, set appart minor UI changes. The team recognized that a simple and functional 'MVP' would be favorable over a more complex (but potentially more error prone) design. At times where the software did not yet work, or issues arised, team members always reacted to help each other and rectify issues. Finally this aspect of agile development was implemented through travis builds, which were required to pass in order for a pull request to be merged into the main branch of our code. This ensured our main app would not get broken and would always be "working software";

### *"Agile processes promote sustainable development. The sponsors, developers, and users should be able to maintain a constant pace indefinitely"*
Multiple agile processes were implemented during the development of the project. Our automatic travis builds allowed for continous testing, while github enabled continous and parallel development. Also our weekly meetings and continuous online discussions meant that everyone always had good sense of the project direction. This in turn enabled us to have pseudo continous deployment, where new working features would be merged into our main branch. In this regard the team could potentially keep developing the app indefinitly at the same pace.
### *"Continuous attention to technical excellence and good design enhances agility"*
In a meeting we surveyed the technical skills and experiences of our team and choose the tools that most members are comfortable working with. Using tools that we are experienced with ensures technical excellence.
Whenever there is a change in design, or a commit to GitHub, it would require approval of other members. That means no design changes are allowed without peer review. Suggestions, discussions help ensure the design is good.
### *"Simplicity - the art of maximizing the amount of work not done - is essential"*
Simplicity means maximizing the amount of unnecessary work not done. During sprint 1, our goal was to develop a shippable product before a deadline. We only made tasks for the high priority user stories and left medium and low priority user stories for the future. The view developed was minimalist, meaning every element is necessary.
The project was of low complexity and requires low amount the testing. Manually testing the app only takes a couple of minutes, so we decided that implementing test automation was unnecessary. A lot of time and effort was saved by keeping testing simple.
To keep communications simple, we set up a discord channel so during meetings, not all members are required to be physically present in the room to attend the meetings. Members could attend the meetings from anywhere that has internet connection and drop in or out at any time convenient.
### *"The best architectures, requirements, and designs emerge from self-organizing teams"*
* How did this apply? <br />
Requirements were laid out during sprint planning, while architecture and designs were decided upon during team meetings. In other words, each of the three aspects were decided upon when a majority of the team members came to a consensus. To reach the agreement, each proposer would explain their point of view in why their proposal were the most beneficial to the project. We would then vote and note down in our weekly minutes, whichever direction the project would head towards.

* How did this evolve? <br />
At first, the choices were proposed from the people the most knowledgeable in their particular fields (i.e. database choice from people with database experiences). However, as the project went along, the people who were actually working on that aspect of the project would then drive or change the design according to needs or necessities.
### *"At regular intervals, the team reflects on how to become more effective, then tunes and adjusts its behaviour accordingly"*
* `Luke`

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
