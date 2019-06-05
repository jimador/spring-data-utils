package org.jimador.sd.util

import spock.lang.Specification

class MoreSpecificationsSpec extends Specification {

    def "test valueIn null extractor"() {
        when:
          def result = MoreSpecifications.valueIn(null, [new Object()])

        then:
          NullPointerException ex = thrown()
          ex.getMessage() == "Expression extractor must not be null"
    }

    def "test valueIn null list"() {
        when:
          def result = MoreSpecifications.valueIn({ x -> null }, null)

        then:
          NullPointerException ex = thrown()
          ex.getMessage() == "Collection must not be null"
    }

    def "test valueIn not null"() {
        when:
          def result = MoreSpecifications.valueIn({ x -> null }, [new Object()])

        then:
          result != null
    }

    def "test equals null extractor"() {
        when:
          def result = MoreSpecifications.equalTo(null, new Object())

        then:
          NullPointerException ex = thrown()
          ex.getMessage() == "Expression extractor must not be null"
    }

    def "test equals null value"() {
        when:
          def result = MoreSpecifications.equalTo({ x -> null }, null)

        then:
          NullPointerException ex = thrown()
          ex.getMessage() == "Value must not be null"
    }

    def "test equals not null"() {
        when:
          def result = MoreSpecifications.equalTo({ x -> null }, new Object())

        then:
          result != null
    }

    def "test like null extractor"() {
        when:
          def result = MoreSpecifications.like(null, "a string")

        then:
          NullPointerException ex = thrown()
          ex.getMessage() == "Expression extractor must not be null"
    }

    def "test like null value"() {
        when:
          def result = MoreSpecifications.like({ x -> null }, null)

        then:
          NullPointerException ex = thrown()
          ex.getMessage() == "Value must not be null"
    }

    def "test like not null"() {
        when:
          def result = MoreSpecifications.like({ x -> null }, "a string")

        then:
          result != null
    }

    def "test startsWith null extractor"() {
        when:
          def result = MoreSpecifications.startsWith(null, "a string")

        then:
          NullPointerException ex = thrown()
          ex.getMessage() == "Expression extractor must not be null"
    }

    def "test startsWith null value"() {
        when:
          def result = MoreSpecifications.startsWith({ x -> null }, null)

        then:
          NullPointerException ex = thrown()
          ex.getMessage() == "Value must not be null"
    }

    def "test startsWith not null"() {
        when:
          def result = MoreSpecifications.startsWith({ x -> null }, "a string")

        then:
          result != null
    }

    def "test startsWith IC null extractor"() {
        when:
          def result = MoreSpecifications.startsWith(null, "a string", true)

        then:
          NullPointerException ex = thrown()
          ex.getMessage() == "Expression extractor must not be null"
    }

    def "test startsWith IC null value"() {
        when:
          def result = MoreSpecifications.startsWith({ x -> null }, null, true)

        then:
          NullPointerException ex = thrown()
          ex.getMessage() == "Value must not be null"
    }

    def "test startsWith IC not null"() {
        when:
          def result = MoreSpecifications.startsWith({ x -> null }, "a string", true)

        then:
          result != null
    }

    def "test distinct null"() {
        when:
          def result = MoreSpecifications.distinct(null)

        then:
          NullPointerException ex = thrown()
          ex.getMessage() == "Specification must not be null"
    }

    def "test distinct not null"() {
        when:
          def result = MoreSpecifications.distinct({ r, c, b -> null })

        then:
          result != null
    }

}
