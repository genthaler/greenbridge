On the [GettingStarted](GettingStarted.md) page, there are two maven projects generated, why is that?

The main reason the workflow that is recommended to be used with Greenbridge.

Greenbridge should be as unobtrusive as possible. The team should be creating the stories and scenarios in a wiki or tracker in whatever manner works for them. Whenever a change is made however, Greenbridge has to harvest the information and make it available to the developers. This is the reason for the first module. Its whole purpose in is to be build by a CI server. What triggers the build is up to the team. There is a hudson plugin provided that will build on a change to a confluence set of pages. For A jira project, the CI server could build on a timed schedule.

The result of this build should be a -SNAPSHOT artifact. What this implies is that the stories and scenarios are in a SNAPSHOT mode. This allows you to import this module into your application and when you run maven with a -U the stories and scenarios will always be updated. This mechanism allows for the tests to break if someone updates a scenario.

Another beneficial effect is that a set of stories and scenarios could be -RELEASED and versioned following the maven convention of a module. This allows a maintenance and support team to see what the requirements where at a point in time, which typically is very hard to do.

Also, since the story module is being built by a CI server, it dramatically cuts down build, compile and test time for each developer.

There also is a SingleProjectArchetype available, but that should be used in single person team settings.

