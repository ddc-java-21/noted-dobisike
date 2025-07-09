---
title: Overview
description: "Project proposal or summary of in-progress/completed project."
order: 0
---

{% include ddc-abbreviations.md %}

## Page contents
{:.no_toc}

- ToC
{:toc}

## Summary

    >Noted is a note taking mobile application. Noted allows users to create notes that they would 
    like to save and go back to so that they can review the notes that they have taken previously.
    Each user can create tasks aka todo's to add to certain dates so that they can have a list 
    of tasks that they wanted to keep track of as well that might coencide with a note or activity
    that they are doing on a particular day. A user can also create reminders to set in the future
    that will give them a countdown of how many days left until that reminder is due as well as 
    being a daily reminder up until that date has come because of the frustration that some people
    have with other reminder apps that only notify them when that date has come and not up until 
    that day has come. The user also has the ability to update or delete any of these notes, tasks, 
    or reminders.
    

## Intended users and user stories

- Busy people who wants to whip out their phone and make quick notes and reminders.
    > As a businessperson, I use Noted so that I can set reminders to keep track of things I need 
    to complete in the future without it slipping my mind.

- Moms who need to keep track of their kids sports games.
    > As a mom, I use Noted so that I can now use the reminder to know which days my kid needs a ride 
    to their game and take notes for the score of their games
  
## Functionality

* The user can select a date on a calendar to open up that days notes, tasks, and reminders
* The user can assign a task to a certain date
* The user can create a title and description for a task
* The user can add images to a note that they have created
* The user can assign a reminder to a certain date
* The user can create a title and description for a task 
* The user can create a title and description for a reminder
* The user can create a note and have the option to assign it to a date if they choose
* The user can edit their created tasks, reminders, or notes

## Persistent data

Using a bullet list, list what content will be stored on the Android device. This should include any information that users of your app would expect to be maintained (i.e., without connection to a server) across multiple sessions of use.

For example, this starter app already includes the necessary data model elements and data-access code to store & retrieve the following 
  
* User
    * Display name
    * OAuth2.0 identifier
    * Timestamp of first login to the app

* Task
    * Task title
    * Task description
    * Timestamp of task creation
    * Assigned task date
    * Completion of task

* Reminder
  * Reminder title
  * Reminder description
  * Timestamp of reminder creation
  * Assigned reminder selected final date
  * Completion of reminder

* Note
  * Note title
  * Note description
  * Timestamp of task creation
  * Optionally assigned note date

* Image
  * Image given to note
  * Timestamp of task creation
    
## Device/external services

* Google cloud console
  * [`Service`](https://console.cloud.google.com/welcome?inv=1&invt=Ab2UqQ&project=carbon-beanbag-463816-j5)
  * Will hande user authentification for sign in
  * This app will be able to be used once initial sign in is complete and they don't sign out
[//]: # (If the client component will need to access special services of the device &#40;e.g., sensors, contacts, messaging&#41;, list them here using a bullet list. Also, if the client component will need to access already-existing external services &#40;e.g., real-time weather data, Open Movie Database, Open Trivia Database&#41;, those should also be listed here; any such references to external services should include links to the main page or API description page for the service.)

## Stretch goals and possible enhancements 

* Add a list of completed tasks, and reminders so it allows the user to go back and see them in a list format
* Add an effect once you select the completable for a task, or reminder
* Add NASA APOD to the background of the calendar date widget matching the APOD to the date widget

