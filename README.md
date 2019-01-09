# App
trial push



This application was designed and written for my cs246 java class. It was a group project over eight (-ish I think) weeks. 
The application would send out reminder sms messages to clients who's appointments were stored in a google account linked to 
the application. When opening up the application, the user could create a new template sms or choose one that was previously 
created and set one of them as the default. The application would connect to a google calendar and check for the upcoming 
appointments; fill in the template with the appointment information, such as a client's name, appointment time and date; send 
an sms message to the client using the number that was stored in the calendar event; and then repeat the process with the rest
of the clients for a specific time period (I believe we had it set for all the appointments set for the next day.).

I mostly worked on the sms sending portion of the codebase. There was a lot of code left in there from when we had it so one could
also manually send an sms message. I remember it was pretty messy, but it worked, and I learned a lot from it.
