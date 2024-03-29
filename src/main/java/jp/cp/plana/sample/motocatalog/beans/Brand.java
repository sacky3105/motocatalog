package jp.cp.plana.sample.motocatalog.beans;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

/**
 * バイクメーカー(ブランド)
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Brand {
    // ブランドID  
    private String brandId;
    // ブランド名  
    private String brandName;
}
