package br.com.blz.testjava.utils

import org.slf4j.Logger
import org.slf4j.LoggerFactory

class CustomLogger {
  companion object {
    val logger: Logger = LoggerFactory.getLogger(CustomLogger::class.java)
  }
}
