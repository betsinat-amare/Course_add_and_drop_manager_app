# Course_add_and_drop_manager_app
# Group members
| Name                | ID            |
|---------------------|---------------|
| Betsinat Amare      | UGR/8102/15   |
| Fenet Abilu         | UGR/5813/15   |
| Selamawit Fikru     | UGR/9724/15   |
| Selome Wondimneh    | UGR/1372/15   |
| Dawit Mekonnen      | UGR/3391/13   |

Course Add and Drop App
Project Overview
The Course Add and Drop App is a mobile application designed to streamline the process of course management for educational institutions. It facilitates two primary user roles: Admin and Student. Admin users can manage course offerings, while students can view available courses, enroll in them, and manage their enrollments efficiently. The application aims to enhance the educational experience by providing a clear and structured method for handling course registrations.

Authentication and Authorization

Authentication: The process through which users verify their identity when they log into the application. Secure logins ensure that only authorized users can access their respective functionalities.

Authorization: Determines which resources a user can access and what actions they can perform within the app. This mechanism ensures that users can only access features relevant to their roles.

Business Features

Feature 1: Course List Management (Admin)
CRUD Operations:

Create: Admins can add new courses with details such as course name, course code, instructor, credits, and schedule.

Read: Admins can view a list of all existing courses along with their details.

Update: Admins can update course information, such as course name, instructor, or schedule as necessary.

Delete: Admins can remove courses from the list that are no longer offered.

Feature 2: Course Catalog (Student)
Description: Students can view a comprehensive list of courses offered by the institution as defined by the admin.

CRUD Operations:

Create (Enroll in Course):

Description: Students can select courses from the catalog to enroll in. The app verifies that the course is available.

Functionality: When a student chooses a course to enroll in, a new enrollment record is created.

Read (View Courses):

Description: Students can see all available courses with details like course name, code, instructor, and schedule.

Update: (This can be interpreted as changing enrollment status)

Description: Students can alter their enrollment status (e.g., switch from "interested" to "enrolled").

Delete (Drop Course):

Description: Students can drop courses they are currently enrolled in.

Functionality: The app updates the enrollment record to reflect that the student is no longer enrolled.

Feature 3: Enrollment Management (Student)

Description: Students can effectively manage their enrollments in courses.

CRUD Operations:

Create (Enrollment Record):

Description: When a student enrolls in a course, an enrollment record is created linking the student and the course.

Read (View Enrolled Courses):

Description: Students can view a list of courses they are currently enrolled in along with relevant details.

Update (Change Enrollment Status):

Description: Students can update their enrollment status (e.g., mark a course as completed).

Delete (Drop Course):

Description: Students can remove courses they are enrolled in, updating their information accordingly.
