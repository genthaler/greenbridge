Greenbridge has been written to make it easy to get across to the land of behaviour driven development. A team can enter stories and scenarios in a wiki or a issue tracker. These are then made available to the developers as annotations in their JUnit test. When someone changes the scenario, the test breaks. The compiler will tell the developer exactly where to look to change the test to match the changed specifications.

Here are some simple examples of Greenbridge works:
  * Use a wiki to enter stories and scenarios. Example (read-only) [wiki](http://greenbridge.googlecode.com/svn/trunk/example-app-stories/stories.html).
  * Write JUnit tests with some simple annotations that refrence the wiki. Example [test](http://greenbridge.googlecode.com/svn/trunk/example-app/target/site/xref-test/com/gc/gb/AppIT.html).
  * Run the maven build with both unit and integration tests and a report will be generated. Example [report](http://greenbridge.googlecode.com/svn/trunk/example-app/target/greenbridge/report/report.html).

See the full [Greenbridge workflow](http://greenbridge-bdd.blogspot.com/2010/03/greenbridge-bdd-pitch.html).

WhyGreenbridge is different.

GettingStarted will help you get going with Greenbridge in two minutes.


There many sources that can be used to hold your stories and scenarios.
  * Out of the box it uses TiddlyWiki.
  * See the JiraPlugin for harvesting from Jira.
  * See the ConfluencePlugin for harvesting from Confluence.
