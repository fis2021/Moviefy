# Moviefy

## Table of contents
* [General description](#general-description)
* [Technologies used](#technologies-used)
* [Signing up for an account and logging in](#signing-up-for-an-account-and-logging-in)
* [The Admin Account](#the-admin-account)
* [The Client Account](#the-client-account)

## General description
Moviefy is a desktop application designed for small cinema businesses and their clients alike. It aids businesses to promote themselves by providing a digital platform while helping film enthusiasts book tickets for their desired movies and discover new titles.

## Technologies used
* Java 15
* JavaFX 15 - UI;
* Gradle - dependencies and build tool;
* Nitrite - database operations;
* [JavaMail](https://javaee.github.io/javamail/) - for sending confirmation e-mails;

## Signing up for an account and logging in
Before using the platform, every user, be they a cinema administrator or a client, must sign up for an account. On registration, there are two types of user accounts, based on the aforementioned roles:
* Cinema Client
* Cinema Administrator
Based on the role of choice, specific functions will be granted to the user after logging in.

If the logging in fails due to a wrong username-password combination, a secure password-changing option is available. Password changing is also available after logging in.

## The Admin Account
After logging in, a venue administrator will be redirected to a list of upcoming movie screenings for his venue. From this page, they can:
* **See booked seats** for each movie screening;
* **Add new screenings**, for both new and existing movies;
* **Cancel movie screenings**;

## The Client Account
After logging in, a client will be redirected to a list of available cinemas. By clicking on one of them, a list of movie screenings scheduled at the respective venue will appear. From this page, they can:
* **Book movie tickets**, by selecting a movie, a screening time, and a number of tickets;
* **See their bookings**, while having the possibility of **deleting** them;
* **See information** regarding the movies;
* **Add reviews** about movies, while having the possibility of **editing** or **deleting** them at a later time;
