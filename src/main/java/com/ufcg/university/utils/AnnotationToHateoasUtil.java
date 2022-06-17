package com.ufcg.university.utils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AnnotationToHateoasUtil {

    /**
     * This method transforms all links annotated in OpenApi format from a specific method of a Class
     * to a list of Hateoas Links.
     * @param classReflect is the class used for reflection, this class must have methods.
     * @param methodName is the method that will be read, this method must have {@link Operation} annotation.
     * @return a list of Hateoas links.
     */
    public static List<Link> getLinksFromMethodClass(Class<?> classReflect, String methodName) {
        Method method = null;
        List<Link> links = new ArrayList<>();
        for (Method methodCursor: classReflect.getDeclaredMethods()) {
            if (methodCursor.getName().equals(methodName)) {
                method = methodCursor;
                break;
            }
        }
        if (method != null) {
            Operation operation = method.getDeclaredAnnotation(Operation.class);
            for (ApiResponse r : operation.responses()) {
                for (io.swagger.v3.oas.annotations.links.Link link : r.links()) {
                    links.add(convertOASLinkToHateoasLink(link));
                }
            }
        }

        return links;
    }

    /**
     * This method converts an annotation Link from OpenAPI to a Link from Hateoas.
     * The format of attribute operationRef in OpenAPI Link should be:
     *      http://hostname/x1/x2/.../xn/{@link RequestMethod}
     * @param linkOAS Link annotation from OpenAPI
     * @return a Link from Hateoas
     */
    private static Link convertOASLinkToHateoasLink(io.swagger.v3.oas.annotations.links.Link linkOAS) {
        String[] paths = linkOAS.operationRef().split("/");
        String[] realPaths = Arrays.copyOf(paths, paths.length-1);
        return Link.of(String.join("/", realPaths))
                .withRel(linkOAS.name())
                .withTitle(linkOAS.description())
                .withType(paths[paths.length-1].toUpperCase());
    }
}
