# Utilities for Spring Data
#### A playground for Spring Data for fun and profit.

### MoreSpecifications
Convinence class for constructing ```org.springframework.data.jpa.domain.Specification``` instances
```java
Specification<MyEntity> someSpec = Specification.where(MoreSpecifications.valueIn(root -> root.get("someThing"), someCollection))
                                                .and(MoreSpecifications.startsWith(root -> root.get("someField"), "foo"))
                                                .or(MoreSpecifications.like(root -> root.get("someOtherField"), "bar"));
```
