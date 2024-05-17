Skip to content
Navigation Menu

shareio-pl
/
shareio-frontend

Code
Commits
Issues
Pull requests 4
Actions
Security 3

Insights

Add Image selector #46
Open
Piotr-Skrobski wants to merge 1 commit into master from Add_Image_Selector
+89 −0
Conversation 0
Commits 1
Checks 0
Files changed 1
Open
Add Image selector
#46
File filter
0 / 1 files viewed
89 changes: 89 additions & 0 deletions 89
src/components/atoms/ImageSelector.vue
Viewed
@@ -0,0 +1,89 @@
<template>
  <div id="image-adder-main">
    <div v-if="!image">
      <font-awesome-icon id="input-icon" :icon="UploadIcon" @click="triggerFileInput" />
      <input type="file" ref="fileInput" accept="image/png, image/jpeg" @change="handleImageUpload"
        style="display: none;" />
    </div>
    <div v-else>
      <img :src="image" id="image-adder-image" alt="Uploaded image" />
      <font-awesome-icon id="input-icon-overlay" :icon="UploadIcon" @click="triggerFileInput" />
      <input type="file" ref="fileInput" accept="image/png, image/jpeg" @change="handleImageUpload"
        style="display: none;" />
    </div>
  </div>
</template>

<script>
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { faFileArrowUp as UploadIcon } from '@fortawesome/free-solid-svg-icons';
export default {
  name: 'ImageSelector',
  components: {
    FontAwesomeIcon
  },
  data() {
    return {
      image: null,
      file_types: ['image/jpeg', 'image/png'],
      UploadIcon: UploadIcon
    };
  },
  methods: {
    triggerFileInput() {
      this.$refs.fileInput.click();
    },
    handleImageUpload(event) {
      const file = event.target.files[0];
      const reader = new FileReader();
      if (file && (this.file_types.includes(file.type))) {
        reader.onload = () => {
          this.image = reader.result;
          this.emitter.emit('image-uploaded', this.image);
        };
        reader.readAsDataURL(file);
      } else {
        console.error('File is not a jpeg or png');
      }
    }
  }
};
</script>

<style>
#image-adder-main {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  width: 100%;
  position: relative;
}

#input-icon {
  font-size: 15rem;
  cursor: pointer;
}

#input-icon-overlay {
  font-size: 15rem;
  cursor: pointer;
  opacity: 0.5;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}

#image-adder-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

img {
  aspect-ratio: 1/1;
}
</style>
Footer
© 2024 GitHub, Inc.
Footer navigation

Terms
Privacy
Security
Status
Docs
Contact

Add Image selector by Piotr-Skrobski · Pull Request #46 · shareio-pl/shareio-frontend
