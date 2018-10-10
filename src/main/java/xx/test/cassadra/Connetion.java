package xx.test.cassadra;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.KeyspaceMetadata;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.PagingState;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.TableMetadata;

public class Connetion {

	public Cluster cluster = null;
	public Session session = null;
	static final int FETCH_SIZE = 10;
	static final int ITEMS_PER_PAGE = 3;

	public void connect() {
		// addContactPoints:cassandra节点ip withPort:cassandra节点端口 默认9042
		// withCredentials:cassandra用户名密码
		// 如果cassandra.yaml里authenticator：AllowAllAuthenticator 可以不用配置
		cluster = Cluster.builder().addContactPoints("139.159.134.42").withPort(9042)
//				.withCredentials("cassandra", "cassandra")
				.build();
		session = cluster.connect();
	}

	public void printMetaMsg() {
		ResultSet rs = session.execute("select release_version from system.local");
		Row row = rs.one();
		String releaseVersion = row.getString("release_version");
		System.out.printf("Cassandra version is: %s%n", releaseVersion);

		Metadata metadata = cluster.getMetadata();
		System.out.printf("Connected to cluster: %s%n", metadata.getClusterName());

		for (Host host : metadata.getAllHosts()) {
			System.out.printf("Datacenter: %s; Host: %s; Rack: %s%n", host.getDatacenter(), host.getAddress(),
					host.getRack());
		}

		for (KeyspaceMetadata keyspace : metadata.getKeyspaces()) {
			for (TableMetadata table : keyspace.getTables()) {
				System.out.printf("Keyspace: %s; Table: %s%n", keyspace.getName(), table.getName());
			}
		}
	}

	/**
	 * Helper class to emulate random paging.
	 *
	 * <p>
	 * Note that it MUST be stateless, because it is cached as a field in our HTTP
	 * handler.
	 */
	static class Pager {
		private final Session session;
		private final int pageSize;

		Pager(Session session, int pageSize) {
			this.session = session;
			this.pageSize = pageSize;
		}

		ResultSet skipTo(Statement statement, int displayPage) {
			// Absolute index of the first row we want to display on the web page. Our goal
			// is that
			// rs.next() returns
			// that row.
			int targetRow = (displayPage - 1) * pageSize;

			ResultSet rs = session.execute(statement);
			// Absolute index of the next row returned by rs (if it is not exhausted)
			int currentRow = 0;
			int fetchedSize = rs.getAvailableWithoutFetching();
			byte[] nextState = rs.getExecutionInfo().getPagingStateUnsafe();

			// Skip protocol pages until we reach the one that contains our target row.
			// For example, if the first query returned 60 rows and our target is row number
			// 90, we know
			// we can skip
			// those 60 rows directly without even iterating through them.
			// This part is optional, we could simply iterate through the rows with the for
			// loop below,
			// but that's
			// slightly less efficient because iterating each row involves a bit of internal
			// decoding.
			while (fetchedSize > 0 && nextState != null && currentRow + fetchedSize < targetRow) {
				statement.setPagingStateUnsafe(nextState);
				rs = session.execute(statement);
				currentRow += fetchedSize;
				fetchedSize = rs.getAvailableWithoutFetching();
				nextState = rs.getExecutionInfo().getPagingStateUnsafe();
			}

			if (currentRow < targetRow) {
				for (@SuppressWarnings("unused")
				Row row : rs) {
					if (++currentRow == targetRow)
						break;
				}
			}
			// If targetRow is past the end, rs will be exhausted.
			// This means you can request a page past the end in the web UI (e.g. request
			// page 12 while
			// there are only
			// 10 pages), and it will show up as empty.
			// One improvement would be to detect that and take a different action, for
			// example redirect
			// to page 10 or
			// show an error message, this is left as an exercise for the reader.
			return rs;
		}
	}

	/**
	 * 
	 * @param tenantId
	 * @param page
	 * @return
	 */
	public PagingState nextPage(String tenantId, PagingState page) {
//		PreparedStatement videosByUser = session.prepare(
//	              "select id,tenant_id,customer_id,type,name,search_text from thingsboard.asset where tenant_id=? ALLOW FILTERING");
//		UUID uid = UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d"); 
//		System.out.println(uid);
//		Statement statement = videosByUser.bind(uid).setFetchSize(5);

		PreparedStatement videosByUser = session.prepare(
				"select id,tenant_id,customer_id,type,name,search_text from thingsboard.asset where tenant_id=1dc25080-79dc-11e8-892c-392804fb32a1 ALLOW FILTERING");
		Statement statement = videosByUser.bind().setFetchSize(5);
		if (page != null)
			statement.setPagingState(page);

		ResultSet rs = session.execute(statement);
		PagingState nextPage = rs.getExecutionInfo().getPagingState();

		int remaining = rs.getAvailableWithoutFetching();
		List<UserVideo> videos = new ArrayList<UserVideo>(remaining);

		if (remaining > 0) {
			for (Row row : rs) {

				UserVideo video = new UserVideo(row.getString("name"));
				System.out.println(row.getString("name"));
				videos.add(video);

				// Make sure we don't go past the current page (we don't want the driver to
				// fetch the next
				// one)
				if (--remaining == 0)
					break;
			}
		}

		if (nextPage != null) {
			System.out.println("》》》》》》》》》》》》》》have next page:" + nextPage);
		}
		return nextPage;
	}

	/**
	 * 
	 * @param tenantId
	 * @param page
	 * @return
	 */
	public List<UserVideo> randomPage(String tenantId, int pageNo) {
		PreparedStatement videosByUser = session.prepare(
				"select id,tenant_id,customer_id,type,name,search_text from thingsboard.asset where tenant_id=1dc25080-79dc-11e8-892c-392804fb32a1 ALLOW FILTERING");
		Statement statement = videosByUser.bind().setFetchSize(FETCH_SIZE);
		
		Pager pager = new Pager(session, ITEMS_PER_PAGE);
		
		if (pageNo == 0) pageNo = 1;
	      ResultSet rs = pager.skipTo(statement, pageNo);

	      List<UserVideo> videos;
	      boolean empty = rs.isExhausted();
	      if (empty) {
	        videos = Collections.emptyList();
	      } else {
	        int remaining = ITEMS_PER_PAGE;
	        videos = new ArrayList<UserVideo>(remaining);
	        for (Row row : rs) {
	          UserVideo video =
	              new UserVideo(row.getString("name"));
	          System.out.println(row.getString("name"));
	          videos.add(video);

	          if (--remaining == 0) break;
	        }
	      }

//	      return new UserVideosResponse(videos, previous, next);
	      return videos;
	}
	
	public static class UserVideo {

		private int id;

		private String name;

		private Date added;

		public UserVideo(String name) {
			this.name = name;
		}

		@SuppressWarnings("unused")
		public int getId() {
			return this.id;
		}

		public String getName() {
			return name;
		}

		@SuppressWarnings("unused")
		public Date getAdded() {
			return added;
		}
	}

	public static void main(String[] args) {
		Connetion connetion = new Connetion();
		try {
			connetion.connect();
//			connetion.printMetaMsg();
//			PagingState pagingState = connetion.nextPage("1dc25080-79dc-11e8-892c-392804fb32a1", null);
//			PagingState pagingState2 = connetion.nextPage("1dc25080-79dc-11e8-892c-392804fb32a1", pagingState);
			
			List<UserVideo> list = connetion.randomPage("1dc25080-79dc-11e8-892c-392804fb32a1", 4);
			System.out.println(list.size());

		} finally {
			// Close the cluster after we’re done with it. This will also close any session
			// that was
			// created from this
			// cluster.
			// This step is important because it frees underlying resources (TCP
			// connections, thread
			// pools...). In a
			// real application, you would typically do this at shutdown (for example, when
			// undeploying
			// your webapp).
			if (connetion.cluster != null)
				connetion.cluster.close();
		}
	}
}
