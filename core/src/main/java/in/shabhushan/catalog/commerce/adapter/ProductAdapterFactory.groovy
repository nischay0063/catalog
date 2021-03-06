package in.shabhushan.catalog.commerce.adapter

import com.adobe.cq.commerce.api.Product
import org.apache.felix.scr.annotations.Component
import org.apache.felix.scr.annotations.Properties
import org.apache.felix.scr.annotations.Property
import org.apache.felix.scr.annotations.Service
import org.apache.sling.api.adapter.AdapterFactory

/**
 * Created by Shashi Bhushan
 *      on 3/31/2017
 *      for catalog
 */
@Component(immediate = true, metatype = false)
@Service(value = AdapterFactory)
@Properties([
    @Property(name = AdapterFactory.ADAPTABLE_CLASSES, value = "com.adobe.cq.commerce.api.Product"),
    @Property(name = AdapterFactory.ADAPTER_CLASSES, value = "in.shabhushan.catalog.commerce.adapter.ProductAdapter")
])
class ProductAdapterFactory implements AdapterFactory {

    @Override
    <AdapterType> AdapterType getAdapter(Object adaptableClass, Class<AdapterType> typeClass) {
        if(adaptableClass instanceof Product && typeClass == ProductAdapter) {
            Product product = (Product)adaptableClass

            return populateAdapterInternal(product)
        }
        return null
    }

    private ProductAdapter populateAdapterInternal(Product product) {
        ProductAdapter adapter = new ProductAdapter()

        adapter
            .setTitle(product.getProperty("jcr:title", String))
            .setDescription(product.getProperty("jcr:description", String))
            .setRating(product.getProperty("rating", String))
            .setStarRating(product.getProperty("starRating", String))
            .setPrice(product.getProperty("price", String))
            .setSummary(product.getProperty("summary", String))
            .setPath(product.path)
            .setNoOfPages(product.getProperty("noOfPages", Integer))
            .setPublishedDate(product.getProperty("publishedDate", Date))

        if(product.image?.src) {
            adapter.setImageSource(product.image.src)
        }

        adapter
    }
}
