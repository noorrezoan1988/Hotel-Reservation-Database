package manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import connection.SQLConnection;
import sampleUser.User;

public class UserManager {
	/**
	 * displaces the Users rows
	 * 
	 * @throws SQLException
	 */
	public static void displayAllRows() throws SQLException {
		String sql = "Select * from user";
		try (Connection conn = SQLConnection.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);) {
			while (rs.next()) {
				StringBuffer bf = new StringBuffer();
				bf.append(rs.getString("uName") + " ");
				bf.append(rs.getInt("uStars"));
				bf.append("\t");
				bf.append(rs.getDate("memberSince"));
				System.out.println(bf.toString());
			}
		}
	}

	/**
	 * adds a user into the mysql table
	 * 
	 * @param user
	 *            user to add into table
	 * @return boolean if the insert was done
	 * @throws SQLException
	 *             error
	 */
	public static boolean insertUser(User user) throws SQLException {
		String sql = "insert into user (uName,uStars, membersince, banned, days, Referrals, refrence) values"
				+ "(?, ?, ?, ?, ?, ?, ?)";
		ResultSet rs = null;
		try (Connection conn = SQLConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql,
						Statement.RETURN_GENERATED_KEYS);) {
			stmt.setString(1, user.getuName());
			stmt.setInt(2, user.getuStars());
			stmt.setTimestamp(3, user.getMemberSince());
			stmt.setBoolean(4, user.isBanned());
			stmt.setInt(5, user.getDays());
			stmt.setInt(6, user.getReferrals());
			stmt.setString(7, user.getRefrence());
			int affected = stmt.executeUpdate();

			if (affected == 1) {
				return true;
			} else {
				System.err.println("No rows affected");
				return false;
			}

		} catch (SQLException e) {
			System.err.println(e);
			return false;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
	}

	/**
	 * updates the user in the sql table
	 * 
	 * @param user
	 *            the user to update
	 * @return boolean if update or not
	 * @throws SQLException
	 *             error
	 */
	public static boolean update(User user) throws SQLException {
		String sql = "Update user set uStars = ?, memberSince = ?, Banned = ?, Days = ?, Referrals = ?, refrence = ?  where uNAME = ?";

		try (Connection conn = SQLConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);) {
			stmt.setInt(1, user.getuStars());
			stmt.setTimestamp(2, user.getMemberSince());
			stmt.setBoolean(3, user.isBanned());
			stmt.setInt(4, user.getDays());
			stmt.setInt(5, user.getReferrals());
			stmt.setString(6, user.getRefrence());
			stmt.setString(7, user.getuName());
			int affected = stmt.executeUpdate();
			if (affected == 1) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			System.err.println(e);
			return false;
		}

	}

	/**
	 * deletes user form table form uID primary key
	 * 
	 * @param userID
	 *            the user to delete form key
	 * @return returns if done or not
	 * @throws Exception
	 *             error
	 */
	public static boolean deleteUser(String uNAME) throws Exception {
		String sql = "Delete from user where uName = ?";
		try (Connection conn = SQLConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);) {
			stmt.setString(1, uNAME);
			int affected = stmt.executeUpdate();
			if (affected == 1) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			System.err.println(e);
			return false;
		}

	}
	

}
