package com.bee42.javalab.bookstore

import spock.lang.Specification
import spock.lang.Unroll

class MySpockSpec extends Specification {

    def setup() {
        println "setup"
    }

    def cleanup() {
        println "cleanup"
    }

    def "failing test"() {
        expect:
        1 == 5
    }

    def "bdd style testing"() {
        given: "some numbers"
        int x = 4
        int y = 9

        when: "adding them up"
        int sum = x + y

        then: "adds up as expected"
        sum == 13
    }

    def "data table test"() {
        when: "adding them up"
        int sum = x + y

        then: "adds up as expected"
        sum == expectedSum

        where:
        x  | y  || expectedSum
        1  | 1  || 2
        1  | -1 || 0
        0  | 1  || 1
        10 | 1  || 11
    }

    @Unroll
    def "#x + #y is #expectedSum"() {
        when: "adding them up"
        int sum = x + y

        then: "adds up as expected"
        sum == expectedSum

        where:
        x  | y  || expectedSum
        1  | 1  || 2
        1  | -1 || 0
        0  | 1  || 1
        10 | 1  || 11
    }

    @Unroll
    def "providing data from files via data pipes: #x"() {
        when: "just dummy stuff"
        def foo = x + "baz"

        then:
        true

        where:
        x << getClass().getResource("/myTest").readLines()
    }

}
