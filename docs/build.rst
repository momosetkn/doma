=======================
Building an application
=======================

.. contents::
   :depth: 3

Before You Start
================

Maven Central Repository
------------------------

You can pull the artifacts of the Doma framework from the Maven central repository.
We provide two artifacts, ``doma-core`` and ``doma-processor``.

The ``doma-core`` artifact is required at runtime and compile-time.
The ``doma-processor`` artifact provides annotation processors and is required at compile-time only.

The group id and artifact id of those artifacts are as follows:

:GroupId: org.seasar.doma
:ArtifactId: doma-core

:GroupId: org.seasar.doma
:ArtifactId: doma-processor

.. _build-with-gradle:

Build with Gradle
=================

Write your build.gradle.kts as follows:

.. code-block:: kotlin

  dependencies {
      implementation("org.seasar.doma:doma-core:2.56.0")
      annotationProcessor("org.seasar.doma:doma-processor:2.56.0")
  }

To simplify your build.script.kts, we recommend that you use the `Doma Compile Plugin`_.

See build.gradle.kts in the `getting-started`_ repository as an example.

Build with IntelliJ IDEA
========================

Use a newer version of IntelliJ IDEA, and then import your project as a Gradle project.

.. _eclipse-build:

Build with Eclipse
==================

Generate eclipse setting files with Gradle, and then import your project into Eclipse.
To generate the setting files, run ``gradle eclipse``.

To simplify your build.script.kts, we recommend that you use the `com.diffplug.eclipse.apt`_.

See build.gradle in the `getting-started`_ repository as an example.

.. _Doma Compile Plugin: https://github.com/domaframework/doma-compile-plugin
.. _com.diffplug.eclipse.apt: https://plugins.gradle.org/plugin/com.diffplug.eclipse.apt
.. _getting-started: https://github.com/domaframework/getting-started
