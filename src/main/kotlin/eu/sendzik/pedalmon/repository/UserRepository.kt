package eu.sendzik.pedalmon.repository

import eu.sendzik.pedalmon.model.User
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface UserRepository : JpaRepository<User, UUID> {
	@Modifying
	@Transactional
	@Query("""
		INSERT INTO users (id, family_name, given_name)
		VALUES (:userId, :givenName, :familyName)
		ON CONFLICT (id) DO UPDATE SET given_name = :givenName, family_name = :familyName
		""",
		nativeQuery = true
	)
	fun upsertUser(userId: UUID, givenName: String, familyName: String)
}
