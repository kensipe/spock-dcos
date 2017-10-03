package com.mesosphere.marthon

import groovyx.net.http.RESTClient
import spock.lang.Shared
import spock.lang.Specification

import static groovy.json.JsonOutput.toJson

class MarathonSpec extends Specification {

    @Shared
    def token = 'dcos config show core.dcos_acs_token'.execute().text[0..-2]
    @Shared
    def url = 'dcos config show core.dcos_url'.execute().text[0..-2]

    def restClient = new RESTClient(url + "/")

    def "marathon version"() {
        given:

        when:
        def response = restClient.get(path: "marathon/v2/info",
                headers: ["Authorization": "token=${token}"])

        then:
        response.data['version'].startsWith("1.5.0")
    }

    def "launch marathon task"() {
        def app = toJson("id": "/sleep",
                "instances": 1,
                "cmd": "sleep 100000000",
                "cpus": 0.01,
                "mem": 32)

        when:
        def response = restClient.post(path: "marathon/v2/apps",
                body: app,
                contentType: "application/json",
                headers: ["Authorization": "token=${token}"])


        then:
        response.data['deployments'][0]['id']

        when:
        (0..30).find {
            def deployments = restClient.get(path: "marathon/v2/deployments",
                    headers: ["Authorization": "token=${token}"]).data
            if (deployments.size() == 0) {
                print("time is: " + it)
                return true
            }
            sleep(1000)
            return false
        }
        def deployments = restClient.get(path: "marathon/v2/deployments",
                headers: ["Authorization": "token=${token}"]).data

        then:
        deployments.size() == 0

        when:
        def tasks = restClient.get(path: "marathon/v2/tasks",
                headers: ["Authorization": "token=${token}"]).data

        then:
        tasks.size() == 1
        tasks['tasks'][0]['appId'] == "/sleep"


        cleanup:
        restClient.delete(path: "marathon/v2/groups/",
                headers: ["Authorization": "token=${token}"])
    }
}
