Popular Movies
==============

Popular Movies is an Android app for the Udacity Nanodegree Project 1 and 2.

### UPDATES:
01-11-2016 : re-designed detail fragment again to accomodate two-pane layout
30-10-2016 : overlooked rubrics guideline for favourites, need to save movie IDs instead
29-10-2016 : re-designed detail fragment to use tabs and pagers to split information as reviews from themoviedb can be very long
22-10-2016 : refactors favourites feature, old code wasn't working efficiently
03-10-2016 : implemented (with several bugs but main function working) "mark as favourite" feature
27-09-2016 : integrated two-pane layout, now responsive when user clicks on a movie
26-09-2016 : re-factor Listview to RecycleView for efficiency as mentioned in webcast video
25-09-2016 : re-factor code to support two-pane layout
16-09-2016 : Now Fetching reviews from themoviedb API
15-09-2016 : Now Fetching real trailer data from the moviedb API to detail layout and created an intent to youtube to watch them
10-09-2016 : Re-designed Detail Layout to accept trailers (at this point, a dummy trailer data) and reviews
29-08-2016 : Re-designed Layouts and Setting option for Sorting criteria
26-08-2016 : App is now connected to themoviedb API
25-08-2016 : All functionalities implemented except for setting options (criteria selection).
24-08-2016 : Created Mock data for testing

### themoviedb API KEY is required.

In order for the Popular Movies app to function properly, an API key for themoviedb.org must be included with the build.

Please replace `<UNIQUE_API_KEY>` with your API key in [USER_HOME]/.gradle/gradle.properties.
