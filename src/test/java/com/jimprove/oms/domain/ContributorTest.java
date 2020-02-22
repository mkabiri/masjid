package com.jimprove.oms.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.jimprove.oms.web.rest.TestUtil;

public class ContributorTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Contributor.class);
        Contributor contributor1 = new Contributor();
        contributor1.setId(1L);
        Contributor contributor2 = new Contributor();
        contributor2.setId(contributor1.getId());
        assertThat(contributor1).isEqualTo(contributor2);
        contributor2.setId(2L);
        assertThat(contributor1).isNotEqualTo(contributor2);
        contributor1.setId(null);
        assertThat(contributor1).isNotEqualTo(contributor2);
    }
}
