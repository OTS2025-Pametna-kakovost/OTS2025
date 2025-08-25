package si.um.feri.measurements.unsafe;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import io.smallrye.mutiny.Uni;
import org.hibernate.reactive.mutiny.Mutiny;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Random;
import java.util.logging.Logger;

@Path("/unsafe")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
public class UnsafeController {

    private static final Logger LOG = Logger.getLogger(UnsafeController.class.getName());

    // Hardcoded secret (Sensitive information in code)
    private static final String DB_PASSWORD = "SuperSecretPassword123!"; // NOSONAR

    @Inject
    Mutiny.SessionFactory sf;

    @GET
    @Path("/products")
    public Uni<Response> vulnerableSql(@QueryParam("name") String name) {
        // SQL Injection via string concatenation (intentionally vulnerable)
        String sql = "SELECT id, name FROM product WHERE name = '" + name + "'";
        LOG.info("Executing (reactive): " + sql);

        return sf.withSession(session ->
                session.createNativeQuery(sql)
                        .getResultList()
                        .map(rows -> {
                            // Leaks secret to logs (Information exposure)
                            LOG.warning("Using DB password: " + DB_PASSWORD); // intentionally bad
                            return Response.ok(rows).build();
                        })
        );
    }

    @POST
    @Path("/hash")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response weakCrypto(String input) {
        // Weak hash (MD5) – Cryptographic algorithm is insecure
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(input.getBytes());
            String hex = Base64.getEncoder().encodeToString(digest);
            return Response.ok(hex).build();
        } catch (NoSuchAlgorithmException e) {
            // Swallowing exception (Empty/overly broad catch)
            return Response.status(500).entity("error").build();
        }
    }

    @GET
    @Path("/random")
    public Response insecureRandom() {
        // Predictable random (java.util.Random instead of SecureRandom)
        int code = new Random().nextInt(999999);
        return Response.ok("{\"code\":" + code + "}").build();
    }

    @GET
    @Path("/npe")
    public String potentialNpe(@QueryParam("x") String x) {
        // Null pointer risk (dereference without check)
        return "len=" + x.length();
    }
}