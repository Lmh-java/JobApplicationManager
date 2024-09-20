# Personal Project: Job Application Manager

Job Application Manager is designed to assist university students with internship applications and research position
application. This software *tracks job applications* submitted by users, *record these applications*, 
*remind users to follow up*, and *records frequent application questions and answers*. This project 
aims to make students' job applications management **easier**. 

As a student who has submitted tons of job applications to various companies, I always lose track on my job applications
, and usually forget to follow them up. Therefore, I am interested in building a handy tool that helps me to manage my 
applications to look for jobs in a more organized way. And, I am willing to release the project to help more university
students with same situations.

## User Stories
- As a user, I want to be able to add applications to the application portfolio.
- As a user, I want to be able to list all my applications in application portfolio.
- As a user, I want to be able to update my application status.
- As a user, I want to be able to see the applications that I need to follow up.
- As a user, I want to be able to add common application questions and corresponding answers to my question 
portfolio, so that write once and use everywhere.
- As a user, I want to be able to list all the application questions and answers in the question portfolio.
- As a user, I want to have an option for saving my application portfolio and question template portfolio to file.
- As a user, I want to have an option for loading my application portfolio and question template portfolio from file,
so that I can keep working on the previous data.

## Instructions for Grader
- You can generate the first required action related to my #1 user story by the following steps
  - Run my application by running the main method from MainGUI class.
  - Click the top button "Application" to switch to the Application Portfolio (Y).
  - Click "Create" button on the right.
  - Fill in the information in the pop-up dialogue, and click "submit".
  - One application (X) is successfully added to the portfolio (Y).
- You can generate the second required action related to my #3 (Modify 'X' in 'Y') user story by the following steps
  - Run my application by running the main method from MainGUI class.
  - Click the top button "Application" to switch to the Application Portfolio (Y).
  - If there is nothing in the portfolio table, please follow the above instruction to create one first.
  - Click to select one application (X) in the table, then click "Modify" button on the right.
  - A pop-up dialogue will request you to change the information of the application.
  - After modifying everything, click "submit", and the information will be updated on the table.
- You can locate my visual component by the following steps
  - Run my application by running the main method from MainGUI class.
  - You should be able to see a splash screen showing for a few seconds. It contains an image.
- You can save the state of my program by the following steps
  - Write something into either "Application" or "Question Template" Portfolio.
  - Click "File" option in the menu.
  - Click "Save" option in the submenu.
- You can reload the state of my program by the following steps
  - Click "File" option in the menu.
  - Click "Load" option in the submenu.

## Phase 4: Task 2
```console
Tue Apr 02 20:52:19 PDT 2024
[App] 4b48c532-5332-4e28-9593-60fab09e92b9 is added to app portfolio
Tue Apr 02 20:52:37 PDT 2024
[App] 4b48c532-5332-4e28-9593-60fab09e92b9 is found in app portfolio by searching 4b48c532-5332-4e28-9593-60fab09e92b9
Tue Apr 02 20:52:37 PDT 2024
[App] 4b48c532-5332-4e28-9593-60fab09e92b9 is updated to [4b48c](Intern): CEO @ Microsoft, link: https://microsoft.com, submit date: Tue Apr 02 20:52:19 PDT 2024, follow-up: Tue Apr 09 20:52:37 PDT 2024, status: Not Heard Back
Tue Apr 02 20:52:41 PDT 2024
[App] 4b48c532-5332-4e28-9593-60fab09e92b9 is removed
Tue Apr 02 20:52:53 PDT 2024
[Question] 85a60b4f-780c-4359-9ddf-0451c3caf318 is added to question portfolio
Tue Apr 02 20:53:06 PDT 2024
[Question] 85a60b4f-780c-4359-9ddf-0451c3caf318 is found in question portfolio by searching 85a60b4f-780c-4359-9ddf-0451c3caf318
Tue Apr 02 20:53:06 PDT 2024
[Question] 85a60b4f-780c-4359-9ddf-0451c3caf318 is updated to Q: What's your name? Again? A: My name is Jeremy
Tue Apr 02 20:53:08 PDT 2024
[Question] 85a60b4f-780c-4359-9ddf-0451c3caf318 is removed
```

Note:  
`[App] 4b48c532-5332-4e28-9593-60fab09e92b9 is added to app portfolio` shows the first required action.  
`[App] 4b48c532-5332-4e28-9593-60fab09e92b9 is updated to [4b48c](Intern): CEO @ Microsoft, link: https://microsoft.com, submit date: Tue Apr 02 20:52:19 PDT 2024, follow-up: Tue Apr 09 20:52:37 PDT 2024, status: Not Heard Back` shows the second required action.

## Phase 4: Task 3
According to the UML diagram of this project, I found there are two refactors could be done to enhance the
overall design:
1. Let's zoom into the design of `ApplicationTableModel`, `QuestionTableModel`, `ApplicationPortfolioPanel`, `QuestionPortfolioPanel`,
and `MainView` classes (on the right bottom corner of the diagram). We can see that all of these classes contains relations to
either `QuestionTemplatePortfolio` or `ApplicationPortfolio`, this is high coupling. Since all of these 5 classes
have some kinds of relationships with each other, we can extract this relationship. In details, we can remove
these relations to either `QuestionTemplatePortfolio` or `ApplicationPortfolio` from `MainView`, `ApplicationPortfolioPanel`, 
and `QuestionPortfolioPanel`. Instead, we can only keep references to the two panel classes inside `MainView` and only keep
references to table models in each panel. Therefore, only table models are connected to different portfolios, which will simplify 
the relationship.
2. Let's focus on the left part of the UML diagram. It is noticeable that the *abstract* class `Application` can be removed.
Since there is only one type of Application (`PositionApplication`), therefore, removing this inheritance relationship
can avoid lots of type casting and lots of confusions. Instead, I can directly use `PositionApplication` as the only
application that exists in this system. If we want to add more types of application besides *research position* and *job position*
we can use an enumeration inside the `PositionApplication` class to identify different types of applications.  

## Citations
- The logo image of this program (located at `data/Logo.png`) is downloaded from 
https://www.flaticon.com/free-icon/folders_2821739?term=files&page=1&position=12&origin=search&related_id=2821739. 

