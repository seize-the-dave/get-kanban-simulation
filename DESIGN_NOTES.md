# Design Notes

Cards have a column (e.g. test) _and_ a class of service (e.g. standard or expedite).  Currently columns own cards which 
makes it easy to enforce constraints.

Cards _could_ have properties of column and CoS instead, or CoS _could_ be a field instead.  How would that enforce a
WIP limit of 1?

```java
Card.setClassOfService(ClassOfService.EXPEDITE)
``` 

Want to _pull_ into a swimlane.  Do we want to model the CoS or the Swimlane?  What stops a card belonging to both?
Same reference, after all.

How can we ignore items that in the expedite CoS?

WIP can be set.minus(set)?

Need a `SwimlaneDirector`?

Pulls from earlier column if it's done.  Director only allows something to enter expedite if there's only 1 (or whatever)
item.

SwimlaneDirectorDecorator is a column which is before Backlog and after Ready to Deploy.  Columns will pull standard items 