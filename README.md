# Decision Survey Platform
A simple decision making tool written in JAVA and HTML.

## Table of contents
- [Description](#description)
- [Installation](#installation)
- [Usage](#usage)

## Description
Web tool to assist in complicated decision making problems, utilising the AHP methodology. This application, extensively uses algorithms in order to solve a problem, by calculating weights for each criterion, factors that represent the criteria, and the alternative solutions of a target, under the influence of a factor. This online application was implemented by using JAVA & HTML, an SQL database and NIST math library, which are used to calculate eigenvalues and eigenvectors that help evaluating the weights for each criteria, factor and alternative.

The application enables the researcher to create a survey, to determine a group of users who participate in the research, to collect responses, and export results. Each user has the opportunity to take part in a survey.
The tool's implementation demonstrates the convenience that a researcher can conduct a survey through a decision-making service, using simple data forms and programming structures. Furthermore, through this service, materialized algorithms of decision-making are provided, in order to help finding the optimal solution in a more rapid and easier way.

## Installation

### Docker engine
For the deployment of the web server, docker engine is required. Docker Engine is an open source containerization technology for building and containerizing your applications. Docker Engine is available on a variety of operation systems (Linux platforms, Windows, etc). Choose your preferred platform's guide from [here](https://docs.docker.com/engine/install/).

### Project download
```
cd something
```
### Docker commands
```
./something
```

## Usage
The tool is user friendly with clear easy to follow procedures.

The site is separated by user accounts and administrator accounts. 

The administrator can:

* Create, modify and delete researches
* Assign users to answer a research
* Extract research results based on the user answers.

On the other hand a user can:
* Answer an assigned research
* View results

### Administrator
The administrator can find all the possible actions in a sidebar in his main screen.

![Admin Main](https://github.com/alexandrakisdimi/decision-making-system/blob/master/screenshots/admin_main.png)

#### Creating a research

The administrator will have to complete a series of simple forms for the creation of a research.
The AHP methodology requires the research to have at least one criterion, at least one factor for each criterion and at least one alternative.
The administrator will have to provide the name and a description for each element.
>Note: The administrator can edit the research before publishing to users, by visiting the `Edit and Publish Research` link in the sidebar

![Create Research](https://github.com/alexandrakisdimi/decision-making-system/blob/master/screenshots/create_research.png)
![Create Criteria](https://github.com/alexandrakisdimi/decision-making-system/blob/master/screenshots/create_criteria.png)
![Create Factors](https://github.com/alexandrakisdimi/decision-making-system/blob/master/screenshots/create_factors.png)
![Create Alternatives](https://github.com/alexandrakisdimi/decision-making-system/blob/master/screenshots/create_alternatives.png)

#### Assigning to users and publishing for answers.

When the research is created, the next step is to assign users to answer the research. This can be achieved by pressing the `Set User to Research` button in the sidebar

![Assign users](https://github.com/alexandrakisdimi/decision-making-system/blob/master/screenshots/assign_to_user.png)


Next we are ready to publish the research. By clicking publish we are letting the system know that the research is ready to receive answers. Once the research is published it cannot be edited anymore. By default, a research is considered complete when the end date, during the creation of the research, has passed. The administrator has the possibility to complete a research prematurely by visiting the `Edit and Publish Research`. When the research is published a new button will appear that allows the administrator to complete the research.

![Publish](https://github.com/alexandrakisdimi/decision-making-system/blob/master/screenshots/publish.png)

#### Generating results of completed research.

When a research is complete, either by passing the end date, or manually by the administrator. We can generate results through the `Extract Results` button in the sidebar.
This will show us a list of researches that have been completed and results have not been extracted yet.

![Generate results](https://github.com/alexandrakisdimi/decision-making-system/blob/master/screenshots/generate_results.png)

Clicking on the link it will start calculating the Eigenvalues and Eigenvectors for each user answer. Then based on the weights derived from the eigenvectors, a ranking of the alternatives is calculated and the final ranking is displayed on the screen.

![Ranking](https://github.com/alexandrakisdimi/decision-making-system/blob/master/screenshots/rankings_of_alternatives.png)

By clicking the Export button, we can get more detailed results in an Excel file.

![Export](https://github.com/alexandrakisdimi/decision-making-system/blob/master/screenshots/extract_to_excel.png)

### User

The user is more simplyfied than the administrator. A user is only able to answer an assigned research and display previous results. For each research a user will have instructions on how to complete his answers via a ranking system.

Example answers for an assigned research below.

![Answers1](https://github.com/alexandrakisdimi/decision-making-system/blob/master/screenshots/user_answer.png)
![Answers2](https://github.com/alexandrakisdimi/decision-making-system/blob/master/screenshots/user_answer2.png)
![Answers3](https://github.com/alexandrakisdimi/decision-making-system/blob/master/screenshots/user_answer3.png)
![Answers4](https://github.com/alexandrakisdimi/decision-making-system/blob/master/screenshots/user_answer4.png)


In the `Completed Researches` tab. The user will see a list of completed researches. By clicking the link he will be able to download the results of the research.

![Previous Research](https://github.com/alexandrakisdimi/decision-making-system/blob/master/screenshots/previous_research.png)
