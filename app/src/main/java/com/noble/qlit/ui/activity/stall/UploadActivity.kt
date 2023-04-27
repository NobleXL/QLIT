package com.noble.qlit.ui.activity.stall

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import coil.compose.rememberImagePainter
import com.amazonaws.auth.AWSCredentials
import com.amazonaws.event.ProgressEvent
import com.amazonaws.event.ProgressListener
import com.amazonaws.regions.Region
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.internal.observers.ConsumerSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber
import timber.log.Timber.DebugTree
import java.io.IOException

/**
 * @author: NobleXL
 * @desc: UploadActivity
 */
class UploadActivity : ComponentActivity() {

    lateinit var vm: UploadViewModel

    private val PHOTO_PICKER_REQUEST_CODE = 2

    private val ACCESS_KEY = "WEvPUwsFJ8cN3sxc"
    private val SECRET_KEY = "PsoPTgDAFtNv82zqRY3FXO9jz39J83wQ"
    private val END_POINT = "http://192.168.254.101:9000"
    private val BUCKET_NAME = "noble"

    private var tags by mutableStateOf(0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.plant(DebugTree())
        vm = ViewModelProvider(this)[UploadViewModel::class.java]
        // 设置界面内容为Compose组件，使用setContent函数
        setContent {
            UploadView(imageUri = vm.imageUri)
        }
    }

    @Composable
    fun UploadView(imageUri: Uri?) {

        val context = LocalContext.current

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            Row {
                // 选择图片按钮
                Button(
                    onClick = { selectImage(context) },
                    modifier = Modifier
                        .size(100.dp),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(Color.LightGray)
                ) {
                    Text(text = "添加商品图片(仅可一张)", textAlign = TextAlign.Center)
                }

                Spacer(modifier = Modifier.width(16.dp))

                // 显示选择的图片
                imageUri?.let {
                    Image(
                        painter = rememberImagePainter(data = it),
                        contentDescription = "Selected Image",
                        modifier = Modifier
                            .size(100.dp)
                            .clip(shape = RoundedCornerShape(8.dp))
                            .background(Color.Transparent),
                        contentScale = ContentScale.FillBounds
                    )
                }
            }

            OutlinedTextField(
                value = vm.itemName,
                onValueChange = { vm.itemName = it },
                label = { Text("商品名称") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = vm.itemPrice,
                onValueChange = { vm.itemPrice = it },
                label = { Text("价格") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = vm.itemPhone,
                onValueChange = { vm.itemPhone = it },
                label = { Text("手机号(选填)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = vm.itemVX,
                onValueChange = { vm.itemVX = it },
                label = { Text("VX(选填)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = vm.itemQQ,
                onValueChange = { vm.itemQQ = it },
                label = { Text("QQ(选填)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                // 发布商品按钮
                Button(
                    onClick = {
                        tags = 0
                        uploadPhoto()
                    },
                    enabled = (imageUri != null),
                    colors = ButtonDefaults.buttonColors(Color.LightGray)
                ) {
                    Text("发布商品")
                }

                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    onClick = {
                        tags = 1
                        uploadPhoto()
                    },
                    enabled = (imageUri != null),
                    colors = ButtonDefaults.buttonColors(Color.LightGray)
                ) {
                    Text("发布兼职")
                }
            }
        }
    }

    // 选择图片
    private fun selectImage(context: Context) {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/*"
        }
        (context as? Activity)?.startActivityForResult(intent, PHOTO_PICKER_REQUEST_CODE)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PHOTO_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
            data?.data?.let { uri ->
                vm.imageUri = uri
            }
        }
    }

    private fun uploadPhoto() {
        val fileUri = vm.imageUri as Uri
        Single.create<Boolean> { emitter ->
            uploadFile(fileUri)
            emitter.onSuccess(true)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(ConsumerSingleObserver({
                Toast.makeText(this, "上传成功", Toast.LENGTH_SHORT).show()
            }) { throwable: Throwable ->
                Toast.makeText(this, "上传失败", Toast.LENGTH_SHORT).show()
                Timber.e(throwable.localizedMessage)
            })
    }


    @Throws(IOException::class)
    private fun uploadFile(uri: Uri) {
        val s3: AmazonS3 = AmazonS3Client(object : AWSCredentials {
            override fun getAWSAccessKeyId(): String {
                return ACCESS_KEY
            }

            override fun getAWSSecretKey(): String {
                return SECRET_KEY
            }
        }, Region.getRegion(Regions.CN_NORTH_1))
        s3.setEndpoint(END_POINT)
        val isExist = s3.doesBucketExist(BUCKET_NAME)
        if (!isExist) {
            s3.createBucket(BUCKET_NAME)
        }
        val contentResolver = contentResolver
        val inputStream = contentResolver.openInputStream(uri)
        val contentLength = inputStream!!.available().toLong()
        val metadata = ObjectMetadata()
        metadata.contentType = "image/jpeg" // 设置content-type。
        metadata.contentLength = contentLength
        val key = System.currentTimeMillis().toString() + ".jpg"
        val putObjectRequest =
            PutObjectRequest(BUCKET_NAME, key, inputStream, metadata)
                .withGeneralProgressListener(object : ProgressListener {
                    var readByte = 0
                    override fun progressChanged(progressEvent: ProgressEvent) {
                        readByte += progressEvent.bytesTransferred.toInt()
                        Timber.d("progressChanged：" + key + "文件上传进度：" + (((readByte / contentLength.toFloat()) * 100).toString() + "%"))
                    }
                })
        s3.putObject(putObjectRequest)
        val urlRequest = GeneratePresignedUrlRequest(BUCKET_NAME, key)
        val url = s3.generatePresignedUrl(urlRequest)
        Timber.d("文件地址：url=>$url")
        Timber.d("文件地址：url=>${url.toString().substringBefore("?")}")
        vm.imgUri = url.toString().substringBefore("?")
        when (tags) {
            0 -> vm.fetchInfo()
            1 -> vm.fetchInfo2()
        }
    }

}