package com.github.barbasa.gatling.git.request
import java.io.File
import java.time.LocalDateTime

import io.gatling.commons.stats.{OK => GatlingOK}
import io.gatling.commons.stats.{KO => GatlingFail}
import io.gatling.commons.stats.Status
import org.eclipse.jgit.api.Git
import org.eclipse.jgit.lib.Repository
import org.eclipse.jgit.storage.file.FileRepositoryBuilder
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider

sealed trait Request {
  def name: String
  def send: Unit
  def url: String
  def user: String
  private val repoName = url.split("/").last
  val workTreeDirectory: File = new File(s"/tmp/test-$user-$repoName")
  private val builder = new FileRepositoryBuilder
  val repository: Repository = builder.setWorkTree(workTreeDirectory).build()

}

object Request {
  def gatlingStatusFromGit(response: Response): Status = {
    response.status match {
      case OK   => GatlingOK
      case Fail => GatlingFail
    }
  }
}

case class Clone(url: String, user: String) extends Request {

  val name = s"Clone: $url"
  def send: Unit = {
    Git.cloneRepository.setURI(url).setDirectory(workTreeDirectory).call()
  }
}

case class Pull(url: String, user: String) extends Request {
  override def name: String = s"Pull: $url"

  override def send: Unit = {
    new Git(repository).pull().call()
  }
}

case class Push(url: String, user: String) extends Request {
  override def name: String = s"Push: $url"
  val uniqueSuffix = s"$user - ${LocalDateTime.now}"

  override def send: Unit = {
    val git = new Git(repository)
    git.add.addFilepattern(s"testfile-$uniqueSuffix").call
    git
      .commit()
      .setMessage(s"Test commit - $uniqueSuffix")
      .call()
    // XXX Make branch configurable
    // XXX Make credential configurable
    git.push
      .setCredentialsProvider(
        new UsernamePasswordCredentialsProvider("admin", "password"))
      .add("HEAD:refs/for/master")
      .call()
  }
}

case class InvalidRequest(url: String, user: String) extends Request {
  override def name: String = "Invalid Request"

  override def send: Unit = {
    throw new Exception("Invalid Git command type")
  }
}

case class Response(status: ResponseStatus)

sealed trait ResponseStatus
case object OK extends ResponseStatus
case object Fail extends ResponseStatus
