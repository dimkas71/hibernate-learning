package ua.selftaught.entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "hart_trophy_winners")
public class HartTrophyWinners {
	
	@Id
	private Long id;
	
	private String season;
	private String winner;
	private String team;
	
	private String position;
	private int win;
	
	HartTrophyWinners() {}

	public HartTrophyWinners(Long id, String season, String winner, String team, String position, int win) {
		super();
		this.id = id;
		this.season = season;
		this.winner = winner;
		this.team = team;
		this.position = position;
		this.win = win;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public String getWinner() {
		return winner;
	}

	public void setWinner(String winner) {
		this.winner = winner;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public int getWin() {
		return win;
	}

	public void setWin(int win) {
		this.win = win;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, position, season, team, win, winner);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HartTrophyWinners other = (HartTrophyWinners) obj;
		return Objects.equals(id, other.id) && Objects.equals(position, other.position)
				&& Objects.equals(season, other.season) && Objects.equals(team, other.team) && win == other.win
				&& Objects.equals(winner, other.winner);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("HartTrophyWinners(id=").append(id).append(", season=").append(season).append(", winner=")
				.append(winner).append(", team=").append(team).append(", position=").append(position).append(", win=")
				.append(win).append(")");
		return builder.toString();
	}
	
	
	
	
	

}
