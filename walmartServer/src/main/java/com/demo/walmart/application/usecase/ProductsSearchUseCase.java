package com.demo.walmart.application.usecase;

import com.demo.walmart.adapter.repository.ProductsRepository;
import com.demo.walmart.application.port.in.ProductsSearchQuery;
import com.demo.walmart.config.exception.BadRequestException;
import com.demo.walmart.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductsSearchUseCase implements ProductsSearchQuery {

    private static final String INVALID_SEARCH_MESSAGE_LENGTH = "Invalid search length";
    private static final String PALINDROME_REGEX = "\\s+";
    private static final String PALINDROME_REPLACE = "";

    @Autowired
    private ProductsRepository productsRepository;

    @Override
    public List<Product> execute(String value) {
        lengthValidation(value);
        return businessValidations(value);
    }

    private void lengthValidation(String value) {
        Optional.ofNullable(value).filter(x -> x.length()<3 && toNumber(value)==null).ifPresent(s -> {
            throw new BadRequestException(INVALID_SEARCH_MESSAGE_LENGTH);
        });
    }

    private List<Product> businessValidations(String value) {
        return isNumberAndPalindromeCheck(value);
    }

    private List<Product> isNumberAndPalindromeCheck(String value) {
        return Optional.ofNullable(toNumber(value))
                .map(this::isPalindrome)
                .map(productsRepository::findById)
                .map(this::doPalindromeDiscount)
                .orElseGet(() -> { return isNumberCheck(value); });
    }

    private List<Product> isNumberCheck(String value) {
        return Optional.ofNullable(toNumber(value))
                .map(productsRepository::findById)
                .orElseGet(() -> { return isTextAndPalindromeCheck(value); });
    }

    private List<Product> isTextAndPalindromeCheck(String value) {
        return Optional.ofNullable(isPalindrome(value))
                .map(productsRepository::findByBrandOrDescription)
                .map(this::doPalindromeDiscount)
                .orElseGet(() -> { return productsRepository.findByBrandOrDescription(value); });
    }

    private Integer toNumber(String num) {
        try {
            return Optional.of(num).map(Integer::parseInt).orElse(null);
        } catch (NumberFormatException nfe) { return null;}
    }

    private String isPalindrome(String text) {
        var clean = Optional.ofNullable(text)
                .map(txt -> txt.replaceAll(PALINDROME_REGEX, PALINDROME_REPLACE))
                .map(String::toLowerCase)
                .orElse(null);

        return Optional.ofNullable(clean)
                .map(StringBuilder::new)
                .map(StringBuilder::reverse)
                .map(StringBuilder::toString)
                .map(reverse -> (reverse.equals(clean) ? text : null))
                .orElse(null);
    }

    private Integer isPalindrome(Integer number) {
        return Optional.ofNullable(number)
                .map(String::valueOf)
                .map(this::isPalindrome)
                .map(Integer::parseInt)
                .orElse(null);
    }

    private List<Product> doPalindromeDiscount(List<Product> products) {
        return products.stream().map(Product::doDiscount).collect(Collectors.toList());
    }

}
