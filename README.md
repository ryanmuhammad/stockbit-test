# stockbit-test

### Number 1.
```
select
	u.id,
	u.username,
	c."name"
from
	"user" u
left join creator c on
	u.parent = c.id
order by u.id;
```

### Number 2.
[Number 2](https://github.com/ryanmuhammad/stockbit-test/blob/master/src/main/java/com/stockbit/test/App.java)

### Number 3.
[Number 3](https://github.com/ryanmuhammad/stockbit-test/blob/master/src/main/java/com/stockbit/test/solution/Solution.java#L9:L22)

### Number 4.
[Number 4](https://github.com/ryanmuhammad/stockbit-test/blob/master/src/main/java/com/stockbit/test/solution/Solution.java#L24:L57)
