package org.kaddiya.gravy.creator.impl

import com.google.inject.Guice
import com.google.inject.Injector
import org.kaddiya.gravy.creator.CreatorApiModule
import org.kaddiya.gravy.creator.DependencyCreator
import org.kaddiya.gravy.model.BuildPhaseType
import org.kaddiya.gravy.model.Dependency
import org.kaddiya.gravy.model.GAV
import spock.lang.Shared
import spock.lang.Specification


class DependancyCreatorImplSpec extends Specification{


    @Shared
    private Injector injector
    private DependencyCreatorImpl dependencyCreator


    def setupSpec(){
        injector = Guice.createInjector(new CreatorApiModule())

    }


    def "Dependency list can not be null"(){
        setup:
            dependencyCreator = injector.getInstance(DependencyCreatorImpl)
        when:
        def dependencyList = dependencyCreator.getScript()

        then:
        assert dependencyList != null : "Depenency list can not be null"
    }


    def "Dependency list can not be empty when addDependency is called"(){
        setup:
        dependencyCreator = injector.getInstance(DependencyCreator)
        when:
        dependencyCreator.addOne(new Dependency(phaseType : BuildPhaseType.COMPILE, gav : new GAV("com.foo", "sample","1.0"), isBuildScript: false))
        then:
        def dependency = dependencyCreator.getScript()
        assert dependency : "Depenency list can not be null or empty"
        assert dependency.contains("compile 'com.foo:sample:1.0'") : "Added depnendcy not found"
    }

    def "Dependency list can not be empty when addDependencies is called"(){
        setup:
        dependencyCreator = injector.getInstance(DependencyCreator)
        when:
            this.dependencyCreator.addList([new Dependency(phaseType : BuildPhaseType.COMPILE, gav : new GAV("com.foo", "sample","1.0"), isBuildScript: false)])
        then:
        def dependency = dependencyCreator.getScript()
        assert dependency : "Depenency list can not be null or empty"
        assert dependency.contains("compile 'com.foo:sample:1.0'") : "Added depnendcy not found"
    }



}
