<template>
  <div id="image-adder-main">
    <div v-if="!image">
      <font-awesome-icon id="input-icon" :icon="UploadIcon" @click="triggerFileInput" />
      <input type="file" ref="fileInput" :accept="file_types.join(', ')" @change="handleImageUpload"
        style="display: none;" />
    </div>
    <div v-else>
      <img :src="image" id="image-adder-image" alt="Uploaded image" />
      <font-awesome-icon id="input-icon-overlay" :icon="UploadIcon" @click="triggerFileInput" />
      <input type="file" ref="fileInput" :accept="file_types.join(', ')" @change="handleImageUpload"
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
      UploadIcon: UploadIcon,
      image: null,
      file_types: ['image/jpeg', 'image/png'],
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
          this.emitter.emit('image-uploaded', file);
        };
        reader.readAsDataURL(file);
      } else {
        this.emitter.emit('error', { error: 'File is not a jpeg or png' });
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
